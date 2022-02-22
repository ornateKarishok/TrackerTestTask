package com.example.trackertesttask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountriesListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private final List<Countries> countriesNamesList;
    private final ArrayList<Countries> arraylist;

    public CountriesListAdapter(Context context, List<Countries> countriesNamesList) {
        mContext = context;
        this.countriesNamesList = countriesNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(countriesNamesList);
    }

    public static class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return countriesNamesList.size();
    }

    @Override
    public Countries getItem(int position) {
        return countriesNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(countriesNamesList.get(position).getCountriesName());
        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        countriesNamesList.clear();
        if (charText.length() == 0) {
            countriesNamesList.addAll(arraylist);
        } else {
            for (Countries wp : arraylist) {
                if (wp.getCountriesName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    countriesNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
