package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.UserStats;
import rx.Observable;

public class Cache {
    private List<UserStats> items;

    public void save(List<UserStats> users) {
        items = users;
    }

    public Observable<List<UserStats>> load(Throwable t) {
        if (items == null) {
            return Observable.error(t);
        } else {
            return Observable.just(items);
        }
    }
}
