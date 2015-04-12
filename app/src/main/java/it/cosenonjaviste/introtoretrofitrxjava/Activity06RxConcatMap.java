package it.cosenonjaviste.introtoretrofitrxjava;

import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Activity06RxConcatMap extends BaseActivity {

    protected void loadItems() {
        service.getTopUsers()
                .flatMapIterable(UserResponse::getItems)
                .limit(5)
                .flatMap(this::loadRepoStats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::add, t -> showError());
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