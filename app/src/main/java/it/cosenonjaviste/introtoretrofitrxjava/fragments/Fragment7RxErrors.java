package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import java.util.List;
import java.util.concurrent.TimeUnit;

import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;
import rx.Observable;

public class Fragment7RxErrors extends BaseRxFragment<UserStats> {

    private static Cache cache = new Cache();

    public Observable<List<UserStats>> loadItems() {
        return service.getTopUsers()
                .retry(2)
                .flatMapIterable(UserResponse::getItems)
                .limit(5)
                .concatMap(this::loadRepoStats)
                .toList()
                .timeout(20, TimeUnit.SECONDS)
                .retry(1)
                .doOnNext(cache::save)
                .onErrorResumeNext(cache::load);
    }

    private Observable<UserStats> loadRepoStats(User user) {
        return Observable.zip(
                service.getTags(user.getId()).retry(3).map(TagResponse::getItems),
                service.getBadges(user.getId()).retry(3).map(BadgeResponse::getItems),
                (tags, badges) -> new UserStats(user, tags, badges)
        );
    }
}