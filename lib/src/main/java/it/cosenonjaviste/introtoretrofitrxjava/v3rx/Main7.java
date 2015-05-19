package it.cosenonjaviste.introtoretrofitrxjava.v3rx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowServiceFactory;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;
import rx.Observable;

public class Main7 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        StackOverflowService service = StackOverflowServiceFactory.createService(StackOverflowService.class, executor);

        service.getTopUsers()
                .flatMapIterable(UserResponse::getItems)
                .limit(5)
                .concatMap(user -> loadUserStats(service, user))
                .toList()
                .finallyDo(executor::shutdown)
                .subscribe(
                        UserStats::printList,
                        Throwable::printStackTrace
                );

        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    private static Observable<UserStats> loadUserStats(StackOverflowService service, User user) {
        return Observable.zip(
                service.getBadges(user.getId()),
                service.getTags(user.getId()),
                (badgeResponse, tagResponse) ->
                        new UserStats(user, tagResponse.getItems(), badgeResponse.getItems())
        );
    }
}
