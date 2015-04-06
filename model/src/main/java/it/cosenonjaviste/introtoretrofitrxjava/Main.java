package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GitHubService service =
                new GitHubServiceStub();
//        createService();

        ExecutorService executor = Executors.newCachedThreadPool();
        Scheduler scheduler = Schedulers.from(executor);

        new TopRepoLoader(service).poll()
                .observeOn(scheduler)
                .timestamp()
                .subscribe(System.out::println, Throwable::printStackTrace, executor::shutdown);

        executor.awaitTermination(200, TimeUnit.SECONDS);
    }


    private static GitHubService createService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com/")
                .build();
        restAdapter.setLogLevel(RestAdapter.LogLevel.BASIC);
        return restAdapter.create(GitHubService.class);
    }
}
