package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subscriptions.Subscriptions;

public class RetainedFragment<T> extends Fragment {

    private Subscription connectableSubscription = Subscriptions.empty();

    private ConnectableObservable<T> observable;

    public RetainedFragment() {
        setRetainInstance(true);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        connectableSubscription.unsubscribe();
    }

    public static <T> RetainedFragment<T> getOrCreate(FragmentActivity activity, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        RetainedFragment<T> fragment = (RetainedFragment<T>) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new RetainedFragment<>();
            fragmentManager.beginTransaction().add(fragment, tag).commit();
        }
        return fragment;
    }

    public boolean isRunning() {
        return observable != null;
    }

    public void bind(ConnectableObservable<T> observable) {
        this.observable = observable;
        connectableSubscription = observable.connect();
    }

    public Observable<T> getObservable() {
        if (observable == null) {
            return Observable.empty();
        }
        return observable;
    }

    public void clear() {
        observable = null;
        connectableSubscription = Subscriptions.empty();
    }
}