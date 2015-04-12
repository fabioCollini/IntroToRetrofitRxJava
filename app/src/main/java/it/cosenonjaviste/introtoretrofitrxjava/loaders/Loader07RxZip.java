package it.cosenonjaviste.introtoretrofitrxjava.loaders;

import android.content.Context;
import android.widget.ArrayAdapter;

import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Loader07RxZip extends DataLoader {

    public void loadItems(ArrayAdapter<Object> adapter, Context context) {
        service.getTopUsers()
                .flatMapIterable(UserResponse::getItems)
                .limit(5)
                .concatMap(this::loadRepoStats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::add, t -> showError(context));
    }

    private Observable<UserStats> loadRepoStats(User user) {
        return Observable.zip(
                service.getTags(user.getId()).map(TagResponse::getItems),
                service.getBadges(user.getId()).map(BadgeResponse::getItems),
                (tags, badges) -> new UserStats(user, tags, badges)
        );
    }
}