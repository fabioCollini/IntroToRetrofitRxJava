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


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<DataLoader> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(
                new Loader01SingleCall(),
                new Loader02MultipleCalls(),
                new Loader03Callbacks(),
                new Loader04RxSingleCall(),
                new Loader05RxFlatMap(),
                new Loader06RxConcatMap(),
                new Loader07RxZip(),
                new Loader08RxErrors()
        ));
        spinner.setAdapter(spinnerAdapter);

        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1) {
            @Override public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(Html.fromHtml(getItem(position).toString()));
                return view;
            }
        };

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataLoader loader = spinnerAdapter.getItem(position);
                adapter.clear();
                loader.loadItems(adapter, MainActivity.this);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ((ListView) findViewById(R.id.list)).setAdapter(adapter);
    }
}
