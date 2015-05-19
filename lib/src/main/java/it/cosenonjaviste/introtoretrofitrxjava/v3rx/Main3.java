package it.cosenonjaviste.introtoretrofitrxjava.v3rx;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowServiceFactory;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;

public class Main3 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        StackOverflowService service = StackOverflowServiceFactory.createService(StackOverflowService.class, executor);

        service.getTopUsers()
                .subscribe(
                        userResponse -> {
                            List<User> users = userResponse.getItems();
                            if (users.size() > 5) {
                                users = users.subList(0, 5);
                            }
                            System.out.println(users);
                            executor.shutdown();
                        },
                        t -> {
                            t.printStackTrace();
                            executor.shutdown();
                        }
                );

        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}
