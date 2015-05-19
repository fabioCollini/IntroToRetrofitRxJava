package it.cosenonjaviste.introtoretrofitrxjava.v3rx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowServiceFactory;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import rx.schedulers.Schedulers;

public class Main6 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        StackOverflowService service = StackOverflowServiceFactory.createService(StackOverflowService.class, executor);

        service.getTopUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(executor))
                .finallyDo(executor::shutdown)
                .map(UserResponse::getItems)
                .map(users -> users.size() > 5 ? users.subList(0, 5) : users)
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace
                );

        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
