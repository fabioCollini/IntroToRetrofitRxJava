package it.cosenonjaviste.introtoretrofitrxjava;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HtmlAdapter extends BaseAdapter {

    private List<Object> items = new ArrayList<>();

    @Override public int getCount() {
        return items.size();
    }

    @Override public Object getItem(int position) {
        return items.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(Html.fromHtml(getItem(position).toString()));
        return textView;
    }

    public void addAll(Collection<?> collection) {
        items.addAll(collection);
        notifyDataSetChanged();
    }

    public List<Object> getItems() {
        return items;
    }
}
