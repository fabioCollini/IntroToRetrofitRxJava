package it.cosenonjaviste.introtoretrofitrxjava;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Activity03Callbacks extends BaseActivity {

    protected void loadItems() {
        service.getTopUsers(new Callback<UserResponse>() {
            @Override public void success(UserResponse repoResponse, Response response) {
                List<User> users = repoResponse.getItems();
                if (users.size() > 5) {
                    users = users.subList(0, 5);
                }
                adapter.addAll(users);
            }

            @Override public void failure(RetrofitError error) {
                showError();
            }
        });
    }
}