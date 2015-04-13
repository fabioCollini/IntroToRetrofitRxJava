package it.cosenonjaviste.introtoretrofitrxjava.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import it.cosenonjaviste.introtoretrofitrxjava.R;
import it.cosenonjaviste.introtoretrofitrxjava.ServiceFactory;
import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowService;

public abstract class BaseFragment extends Fragment {

    protected StackOverflowService service = ServiceFactory.createService();

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

    public abstract void loadItems(ArrayAdapter<Object> adapter);

    protected void showError() {
        Toast.makeText(getActivity(), R.string.error_loading, Toast.LENGTH_LONG).show();
    }

    @Override public String toString() {
        return getClass().getSimpleName();
    }
}
