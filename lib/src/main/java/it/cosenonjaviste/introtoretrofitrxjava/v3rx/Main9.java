package it.cosenonjaviste.introtoretrofitrxjava.v3rx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowServiceFactory;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import rx.Observable;
import rx.Subscription;

public class Main9 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        StackOverflowService service = StackOverflowServiceFactory.createService(StackOverflowService.class, executor);

        Observable<UserResponse> observable = service.getTopUsers();

        Subscription s1 = observable
                .finallyDo(executor::shutdown)
                .subscribe(System.out::println, Throwable::printStackTrace);
        Subscription s2 = observable
                .finallyDo(executor::shutdown)
                .subscribe(System.out::println, Throwable::printStackTrace);

        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
