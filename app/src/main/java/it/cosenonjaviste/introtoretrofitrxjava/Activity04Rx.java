package it.cosenonjaviste.introtoretrofitrxjava;

import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Activity04Rx extends BaseActivity {

    protected void loadItems() {
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
                .subscribe(adapter::addAll, t -> showError());
    }
}