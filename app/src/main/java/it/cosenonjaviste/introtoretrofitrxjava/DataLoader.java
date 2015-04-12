package it.cosenonjaviste.introtoretrofitrxjava;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public abstract class DataLoader {

    protected StackOverflowService service;

    public DataLoader() {
        service = ServiceFactory.createService(StackOverflowService.class);
    }

    protected abstract void loadItems(ArrayAdapter<Object> adapter, Context context);

    protected void showError(Context context) {
        Toast.makeText(context, R.string.error_loading, Toast.LENGTH_LONG).show();
    }

    @Override public String toString() {
        return getClass().getSimpleName();
    }
}
