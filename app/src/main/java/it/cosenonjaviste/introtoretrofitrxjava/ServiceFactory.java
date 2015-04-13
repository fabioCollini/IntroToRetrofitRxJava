package it.cosenonjaviste.introtoretrofitrxjava;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class ServiceFactory {

    public static StackOverflowService createService() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.stackexchange.com/2.2/")
                .setClient(new OkClient(client))
                .setRequestInterceptor(request -> {
                    request.addQueryParam("site", "stackoverflow");
                    request.addQueryParam("key", "fruiv4j48P0HjSJ8t7a8Gg((");
                })
                .build();
        restAdapter.setLogLevel(RestAdapter.LogLevel.BASIC);
        return restAdapter.create(StackOverflowService.class);
    }
}
