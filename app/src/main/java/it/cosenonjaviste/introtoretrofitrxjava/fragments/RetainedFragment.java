package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import rx.Observable;
import rx.observables.ConnectableObservable;

public class RetainedFragment<T> extends Fragment {

    private ObservableHolder<T> object;

    public RetainedFragment() {
        setRetainInstance(true);
        object = new ObservableHolder<>();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        object.destroy();
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
        return object.isRunning();
    }

    public void bind(ConnectableObservable<T> replay) {
        object.bind(replay);
    }

    public Observable<T> getObservable() {
        return object.getObservable();
    }

    public void clear() {
        object.clear();
    }
}