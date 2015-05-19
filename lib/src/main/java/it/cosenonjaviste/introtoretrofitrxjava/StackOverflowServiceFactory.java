package it.cosenonjaviste.introtoretrofitrxjava;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.Executor;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class StackOverflowServiceFactory {
    public static <T> T createService(Class<T> serviceClass, Executor executor) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.stackexchange.com/2.2/")
                .setClient(new OkClient(new OkHttpClient()))
                .setExecutors(executor, executor)
                .setRequestInterceptor(request -> {
                    request.addQueryParam("site", "stackoverflow");
                    request.addQueryParam("key", "fruiv4j48P0HjSJ8t7a8Gg((");
                })
                .build();
        restAdapter.setLogLevel(RestAdapter.LogLevel.BASIC);
        return restAdapter.create(serviceClass);
    }
}
