package it.cosenonjaviste.introtoretrofitrxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = new ListView(this);
        List<Class<?>> classes = Arrays.asList(
                Activity01SingleCall.class,
                Activity02MultipleCalls.class,
                Activity03Callbacks.class,
                Activity04Rx.class,
                Activity05RxFlatMap.class,
                Activity06RxConcatMap.class,
                Activity07RxZip.class,
                Activity08RxErrors.class
        );
        listView.setAdapter(new ArrayAdapter<Class<?>>(this, android.R.layout.simple_list_item_1, classes) {
            @Override public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(getItem(position).getSimpleName());
                return view;
            }
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Class<?> c = classes.get(position);
            startActivity(new Intent(MainActivity.this, c));
        });
        setContentView(listView);
    }
}
