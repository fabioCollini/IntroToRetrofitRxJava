package it.cosenonjaviste.introtoretrofitrxjava.loaders;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import it.cosenonjaviste.introtoretrofitrxjava.R;
import it.cosenonjaviste.introtoretrofitrxjava.StackOverflowService;

public abstract class DataLoader {

    protected StackOverflowService service;

    public DataLoader(StackOverflowService service) {
        this.service = service;
    }

    public abstract void loadItems(ArrayAdapter<Object> adapter, Context context);

    protected void showError(Context context) {
        Toast.makeText(context, R.string.error_loading, Toast.LENGTH_LONG).show();
    }

    @Override public String toString() {
        return getClass().getSimpleName();
    }
}
