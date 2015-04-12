package it.cosenonjaviste.introtoretrofitrxjava;

import android.os.AsyncTask;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.User;

public class Activity01SingleCall extends BaseActivity {

    @Override protected void loadItems() {
        new AsyncTask<Void, Void, List<User>>() {
            @Override protected List<User> doInBackground(Void... params) {
                try {
                    return loadItemsSync();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override protected void onPostExecute(List<User> users) {
                if (users != null) {
                    adapter.addAll(users);
                } else {
                    showError();
                }
            }
        }.execute();
    }

    private List<User> loadItemsSync() {
        List<User> users = service.getTopUsersSync().getItems();
        if (users.size() > 5) {
            users = users.subList(0, 5);
        }
        return users;
    }
}