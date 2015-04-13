package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import rx.Observable;

public class Fragment4RxSingleCall extends BaseRxFragment<User> {

    public Observable<List<User>> loadItems() {
        return service.getTopUsers()
                .map(UserResponse::getItems)
                .map(users -> {
                    if (users.size() > 5) {
                        return users.subList(0, 5);
                    } else {
                        return users;
                    }
                });
    }
}