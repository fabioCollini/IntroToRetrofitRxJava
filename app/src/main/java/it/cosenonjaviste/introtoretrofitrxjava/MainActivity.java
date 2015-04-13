package it.cosenonjaviste.introtoretrofitrxjava;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import it.cosenonjaviste.introtoretrofitrxjava.loaders.DataLoader;
import it.cosenonjaviste.introtoretrofitrxjava.loaders.Loader01SingleCall;
import it.cosenonjaviste.introtoretrofitrxjava.loaders.Loader02MultipleCalls;
import it.cosenonjaviste.introtoretrofitrxjava.loaders.Loader03Callbacks;
import it.cosenonjaviste.introtoretrofitrxjava.loaders.Loader04RxSingleCall;
import it.cosenonjaviste.introtoretrofitrxjava.loaders.Loader05RxFlatMap;
import it.cosenonjaviste.introtoretrofitrxjava.loaders.Loader06RxConcatMap;
import it.cosenonjaviste.introtoretrofitrxjava.loaders.Loader07RxZip;
import it.cosenonjaviste.introtoretrofitrxjava.loaders.Loader08RxErrors;
import it.cosenonjaviste.introtoretrofitrxjava.loaders.RxDataLoader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        StackOverflowService service = ServiceFactory.createService();
        ArrayAdapter<Object> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(
                new Loader01SingleCall(service),
                new Loader02MultipleCalls(service),
                new Loader03Callbacks(service),
                new Loader04RxSingleCall(service),
                new Loader05RxFlatMap(service),
                new Loader06RxConcatMap(service),
                new Loader07RxZip(service),
                new Loader08RxErrors(service)
        ));
        spinner.setAdapter(spinnerAdapter);

        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1) {
            @Override public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(Html.fromHtml(getItem(position).toString()));
                return view;
            }
        };

        ((ListView) findViewById(R.id.list)).setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object loader = spinnerAdapter.getItem(position);
                adapter.clear();
                if (loader instanceof DataLoader) {
                    ((DataLoader) loader).loadItems(adapter, MainActivity.this);
                } else if (loader instanceof RxDataLoader) {
                    RxDataLoader<?> rxDataLoader = (RxDataLoader<?>) loader;
                    rxDataLoader
                            .loadItems()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(adapter::addAll, t -> showError());
                }
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    protected void showError() {
        Toast.makeText(this, R.string.error_loading, Toast.LENGTH_LONG).show();
    }
}
