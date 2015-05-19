package it.cosenonjaviste.introtoretrofitrxjava.v3rx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class Main8 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        Subscription subscription = Observable
                .interval(1, TimeUnit.SECONDS, Schedulers.from(executor))
                .timestamp()
                .finallyDo(executor::shutdown)
                .subscribe(System.out::println);

        Thread.sleep(2500);

        subscription.unsubscribe();

        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
