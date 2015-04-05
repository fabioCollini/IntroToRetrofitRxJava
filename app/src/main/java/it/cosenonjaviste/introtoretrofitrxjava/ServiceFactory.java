package it.cosenonjaviste.introtoretrofitrxjava;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import it.cosenonjaviste.introtoretrofitrxjava.model.Repo;
import it.cosenonjaviste.introtoretrofitrxjava.model.RepoResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.Observable;
import rx.schedulers.Schedulers;

public class ServiceFactory {

    private static Random random = new Random();

    public static <T> T createService(Class<T> serviceClass) {
        String endpoint = createMockWebServer();

        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setClient(new OkClient(client))
                .build();
        restAdapter.setLogLevel(RestAdapter.LogLevel.BASIC);
        return restAdapter.create(serviceClass);
    }

    private static String createMockWebServer() {
        MockWebServer server = new MockWebServer();
        server.setDispatcher(new Dispatcher() {
            @Override public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                Object response = createResponse(request.getPath());
                Thread.sleep(random());
                return new MockResponse().setBody(new Gson().toJson(response));
            }
        });
        return Observable.<String>create(subscriber -> {
            try {
                server.start();
                String url = server.getUrl("/").toString();
                subscriber.onNext(url);
                subscriber.onCompleted();
            } catch (IOException e) {
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.io()).toBlocking().first();
    }

    private static Object createResponse(String url) {
        if (url.startsWith("/search")) {
            return new RepoResponse(createRepo(1), createRepo(2), createRepo(3), createRepo(4), createRepo(5), createRepo(6));
        } else if (url.endsWith("contributors")) {
            ArrayList<Object> list = new ArrayList<>();
            for (int i = 0; i < random.nextInt(5) + 1; i++) {
                list.add(createUser(random.nextInt(1000)));
            }
            return list;
        } else {
            if (random.nextBoolean()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("kotlin", "123");
                map.put("java", "12345");
                return map;
            } else {
                return Collections.singletonMap("java", "12345");
            }
        }
    }

    private static int random() {
        return 500 + random.nextInt(500);
    }

    private static Repo createRepo(int id) {
        return new Repo(id, "repo " + id, createUser(id));
    }

    private static User createUser(int id) {
        return new User(id, "user " + id);
    }
}
