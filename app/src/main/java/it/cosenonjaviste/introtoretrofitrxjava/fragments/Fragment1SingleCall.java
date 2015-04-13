package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import it.cosenonjaviste.introtoretrofitrxjava.model.User;

public class Fragment1SingleCall extends BaseFragment {

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView view = new ListView(getActivity());

        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(getActivity(), android.R.layout.simple_list_item_1) {
            @Override public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(Html.fromHtml(getItem(position).toString()));
                return view;
            }
        };

        view.setAdapter(adapter);

        loadItems(adapter);
        return view;
    }

    @Override public void loadItems(ArrayAdapter<Object> adapter) {
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