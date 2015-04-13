package it.cosenonjaviste.introtoretrofitrxjava.loaders;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowService;
import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;
import rx.Observable;

public class Loader06RxConcatMap extends RxDataLoader<UserStats> {

    public Loader06RxConcatMap(StackOverflowService service) {
        super(service);
    }

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