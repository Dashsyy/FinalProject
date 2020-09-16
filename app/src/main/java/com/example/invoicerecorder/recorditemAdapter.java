package com.example.invoicerecorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class recorditemAdapter extends ArrayAdapter<record_item> implements AdapterView.OnItemSelectedListener {
    private Context mContext;
    int mResource;
    TextView item_name,item_price;
    public recorditemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<record_item> objects) {
        super(context, resource, objects);
        mContext =context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        String itemName = getItem(position).getFirstName();
        String itemPrice = getItem(position).getLastName();

        //ceate record_item object;
        record_item recordItem = new record_item(itemName,itemPrice);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        item_name = convertView.findViewById(R.id.TxtViewItem);
        item_price = convertView.findViewById(R.id.TxtViewPrice);

        item_name.setText(itemName);
        item_price.setText(itemPrice);
        return convertView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
