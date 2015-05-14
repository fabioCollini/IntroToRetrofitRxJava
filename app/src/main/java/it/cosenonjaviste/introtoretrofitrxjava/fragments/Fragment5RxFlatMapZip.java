package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;
import rx.Observable;

public class Fragment5RxFlatMapZip extends BaseRxFragment<UserStats> {

    public Observable<List<UserStats>> loadItems() {
        return service.getTopUsers()
                .flatMapIterable(UserResponse::getItems)
                .limit(5)
                .flatMap(this::loadUserStats)
                .toList();
    }

    private Observable<UserStats> loadUserStats(User user) {
        return Observable.zip(
                service.getTags(user.getId()),
                service.getBadges(user.getId()),
                (tags, badges) -> new UserStats(user, tags.getItems(), badges.getItems())
        );
//        or using map:
//        return Observable.zip(
//                service.getTags(user.getId()).map(TagResponse::getItems),
//                service.getBadges(user.getId()).map(BadgeResponse::getItems),
//                (tags, badges) -> new UserStats(user, tags, badges)
//        );
    }
}