package it.cosenonjaviste.introtoretrofitrxjava;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;

import it.cosenonjaviste.introtoretrofitrxjava.fragments.Fragment1SingleCall;
import it.cosenonjaviste.introtoretrofitrxjava.fragments.Fragment2MultipleCalls;
import it.cosenonjaviste.introtoretrofitrxjava.fragments.Fragment3Callbacks;
import it.cosenonjaviste.introtoretrofitrxjava.fragments.Fragment4RxSingleCall;
import it.cosenonjaviste.introtoretrofitrxjava.fragments.Fragment5RxFlatMap;
import it.cosenonjaviste.introtoretrofitrxjava.fragments.Fragment6RxConcatMap;
import it.cosenonjaviste.introtoretrofitrxjava.fragments.Fragment7RxZip;
import it.cosenonjaviste.introtoretrofitrxjava.fragments.Fragment8RxErrors;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(
                Fragment1SingleCall.class.getSimpleName(),
                Fragment2MultipleCalls.class.getSimpleName(),
                Fragment3Callbacks.class.getSimpleName(),
                Fragment4RxSingleCall.class.getSimpleName(),
                Fragment5RxFlatMap.class.getSimpleName(),
                Fragment6RxConcatMap.class.getSimpleName(),
                Fragment7RxZip.class.getSimpleName(),
                Fragment8RxErrors.class.getSimpleName()
        ));
        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = Fragment.instantiate(MainActivity.this, "it.cosenonjaviste.introtoretrofitrxjava.fragments." + spinnerAdapter.getItem(position));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .commit();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
