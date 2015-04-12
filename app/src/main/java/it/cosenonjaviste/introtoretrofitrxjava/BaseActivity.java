package it.cosenonjaviste.introtoretrofitrxjava;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends ActionBarActivity {

    protected ArrayAdapter<Object> adapter;

    protected StackOverflowService service;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1) {
            @Override public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(Html.fromHtml(getItem(position).toString()));
                return view;
            }
        };
        ((ListView) findViewById(R.id.list)).setAdapter(adapter);

        service = ServiceFactory.createService(StackOverflowService.class);

        loadItems();
    }

    protected abstract void loadItems();

    protected void showError() {
        Toast.makeText(this, R.string.error_loading, Toast.LENGTH_LONG).show();
    }
}
