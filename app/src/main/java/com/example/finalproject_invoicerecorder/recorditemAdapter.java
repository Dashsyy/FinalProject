package com.example.finalproject_invoicerecorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class recorditemAdapter extends ArrayAdapter<record_item>{
    private Context mContext;
    int mResource;
    TextView item_name,item_price,item_id;
    public recorditemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<record_item> objects) {
        super(context, resource, objects);
        mContext =context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        String itemuID = getItem(position).getiD();
        String itemName = getItem(position).getItem();
        String itemPrice = getItem(position).getPrice();

        //create record_item object;
        record_item recordItem = new record_item(itemuID,itemName,itemPrice);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        //Find the textview in listview
        item_id = convertView.findViewById(R.id.TxtViewId);
        item_name = convertView.findViewById(R.id.TxtViewItem);
        item_price = convertView.findViewById(R.id.TxtViewPrice);

        //set text in listview
        item_id.setText(itemuID);
        item_name.setText(itemName);
        item_price.setText(itemPrice);
        return convertView;
    }


}
