package com.ajcb.hp.onlinefarmersmarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapterList extends ArrayAdapter<Item> {
    ArrayList<Item> Buyers = new ArrayList<>();

    public MyAdapterList(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        Buyers = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_view_items, null);
        TextView textView1 = (TextView) v.findViewById(R.id.textView1);
        TextView textView2 = (TextView) v.findViewById(R.id.textView2);
        TextView textView3 = (TextView) v.findViewById(R.id.textView3);

        textView1.setText(Buyers.get(position).getNp());
        textView2.setText(Buyers.get(position).getAdd());
        textView3.setText("Stock alloted: "+Buyers.get(position).getSto()+"      DueDate: "+Buyers.get(position).getDt());


        return v;

    }
}
