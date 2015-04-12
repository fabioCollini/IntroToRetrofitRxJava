package it.cosenonjaviste.introtoretrofitrxjava;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import it.cosenonjaviste.introtoretrofitrxjava.model.BadgeResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.TagResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Loader08RxErrors extends DataLoader {

    private List<UserStats> cache;

    protected void loadItems(ArrayAdapter<Object> adapter, Context context) {
        service.getTopUsers()
                .retry(3)
                .flatMapIterable(UserResponse::getItems)
                .limit(5)
                .concatMap(this::loadRepoStats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .timeout(20, TimeUnit.SECONDS)
                .retry(2)
                .doOnNext(this::saveOnCache)
                .onErrorResumeNext(this::loadFromCache)
                .subscribe(adapter::addAll, t -> showError(context));
    }

    private void saveOnCache(List<UserStats> users) {
        cache = users;
    }

    private Observable<List<UserStats>> loadFromCache(Throwable t) {
        if (cache == null) {
            return Observable.error(t);
        } else {
            return Observable.just(cache);
        }
    }

    private Observable<UserStats> loadRepoStats(User user) {
        return Observable.zip(
                service.getTags(user.getId()).retry(3).map(TagResponse::getItems),
                service.getBadges(user.getId()).retry(3).map(BadgeResponse::getItems),
                (tags, badges) -> new UserStats(user, tags, badges)
        );
    }
}