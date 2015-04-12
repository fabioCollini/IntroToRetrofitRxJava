package it.cosenonjaviste.introtoretrofitrxjava;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.User;

public class Loader01SingleCall extends DataLoader {

    @Override protected void loadItems(ArrayAdapter<Object> adapter, Context context) {
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
                    showError(context);
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