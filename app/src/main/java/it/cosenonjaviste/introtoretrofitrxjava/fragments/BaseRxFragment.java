package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.HtmlAdapter;
import it.cosenonjaviste.introtoretrofitrxjava.R;
import it.cosenonjaviste.introtoretrofitrxjava.ServiceFactory;
import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowService;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class BaseRxFragment<T> extends Fragment {

    public static final String ITEMS = "items";
    protected StackOverflowService service = ServiceFactory.createService();
    private HtmlAdapter adapter;
    private Subscription subscription = Subscriptions.empty();
    private RetainedFragment<List<T>> retainedFragment;
    private ListView listView;
    private View progressLayout;
    private View errorLayout;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);

        listView = (ListView) view.findViewById(R.id.list);
        progressLayout = view.findViewById(R.id.progress);
        errorLayout = view.findViewById(R.id.error_layout);

        adapter = new HtmlAdapter();

        listView.setAdapter(adapter);

        retainedFragment = RetainedFragment.getOrCreate(getActivity(), "retained");

        if (savedInstanceState != null) {
            List<T> savedItems = Parcels.unwrap(savedInstanceState.getParcelable(ITEMS));
            if (savedItems != null) {
                showDataInList(savedItems);
            } else if (retainedFragment.isRunning()) {
                showProgress();
            } else {
                showError();
            }
        } else {
            showProgress();
            Observable<List<T>> observable = loadItems()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            retainedFragment.bind(observable.replay());
        }

        return view;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ITEMS, Parcels.wrap(adapter.getItems()));
    }

    @Override public void onResume() {
        super.onResume();
        subscription = retainedFragment
                .getObservable()
                .finallyDo(retainedFragment::clear)
                .subscribe(this::showDataInList, t -> showError());
    }

    private void showDataInList(List<T> items) {
        adapter.addAll(items);
        errorLayout.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.INVISIBLE);
    }

    @Override public void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }

    private void showProgress() {
        errorLayout.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);
        progressLayout.setVisibility(View.VISIBLE);
    }

    private void showError() {
        errorLayout.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        progressLayout.setVisibility(View.INVISIBLE);
    }

    public abstract Observable<List<T>> loadItems();
}
