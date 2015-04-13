package it.cosenonjaviste.introtoretrofitrxjava.loaders;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowService;
import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import rx.Observable;

public class Loader04RxSingleCall extends RxDataLoader<User> {

    public Loader04RxSingleCall(StackOverflowService service) {
        super(service);
    }

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