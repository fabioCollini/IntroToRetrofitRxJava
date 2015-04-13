package it.cosenonjaviste.introtoretrofitrxjava.loaders;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowService;
import rx.Observable;

public abstract class RxDataLoader<T> {

    protected StackOverflowService service;

    public RxDataLoader(StackOverflowService service) {
        this.service = service;
    }

    public abstract Observable<List<T>> loadItems();

    @Override public String toString() {
        return getClass().getSimpleName();
    }
}
