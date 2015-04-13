package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import android.widget.ArrayAdapter;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.User;
import it.cosenonjaviste.introtoretrofitrxjava.model.UserResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Fragment3Callbacks extends BaseFragment {

    public void loadItems(ArrayAdapter<Object> adapter) {
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