package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;
import rx.Observable;

public class Fragment5RxFlatMap extends BaseRxFragment<UserStats> {

    public Observable<List<UserStats>> loadItems() {
        return service.getTopUsers()
                .flatMapIterable(UserResponse::getItems)
                .limit(5)
                .flatMap(this::loadRepoStats)
                .toList();
    }

    private Observable<UserStats> loadRepoStats(User user) {
        return service.getTags(user.getId())
                .map(TagResponse::getItems)
                .flatMap(tags ->
                                service.getBadges(user.getId())
                                        .map(BadgeResponse::getItems)
                                        .map(badges -> new UserStats(user, tags, badges))
                );
    }
}