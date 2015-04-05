package it.cosenonjaviste.introtoretrofitrxjava;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public abstract class BaseActivity<T, S> extends ActionBarActivity {

    protected ArrayAdapter<T> adapter;

    protected S service;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        ((ListView) findViewById(R.id.list)).setAdapter(adapter);

        service = ServiceFactory.createService(getServiceClass());

        loadItems();
    }

    protected abstract Class<S> getServiceClass();

    protected abstract void loadItems();

    protected void showError() {
        Toast.makeText(this, R.string.error_loading, Toast.LENGTH_LONG).show();
    }
}
