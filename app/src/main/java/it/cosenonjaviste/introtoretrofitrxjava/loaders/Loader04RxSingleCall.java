package it.cosenonjaviste.introtoretrofitrxjava.loaders;

import android.content.Context;
import android.widget.ArrayAdapter;

import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Loader04RxSingleCall extends DataLoader {

    public void loadItems(ArrayAdapter<Object> adapter, Context context) {
        service.getTopUsers()
                .map(UserResponse::getItems)
                .map(users -> {
                    if (users.size() > 5) {
                        return users.subList(0, 5);
                    } else {
                        return users;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addAll, t -> showError(context));
    }
}