package com.example.finalproject_invoicerecorder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class record_item_fragment extends AppCompatDialogFragment {
    DatabaseReference databaseReference;

    EditText itemName;
    EditText itemPrice;
    EditText itemQuantity;
    TextView dateandtime;
    String emailForIdFromMain;
    String currentDateString;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        //To get a Fragment when click "+" button
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.record_item_by_fragment,null);

        itemName = view.findViewById(R.id.EditTxtItem);
        itemPrice = view.findViewById(R.id.EditTxtPrice);
        itemQuantity = view.findViewById(R.id.EditTxtQuantity);
        dateandtime = view.findViewById(R.id.btnDateAndTime);
        //to get data from mainactivity to fragment
        MainActivity activity = (MainActivity) getActivity();
        emailForIdFromMain = activity.getEmailForId();


        builder.setView(view).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Record", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Send data to firebase here
                databaseReference = FirebaseDatabase.getInstance().getReference().child(emailForIdFromMain).child("invoice");
               //generate random key
               String key= databaseReference.push().getKey();
                String item= itemName.getText().toString();
                String price= itemPrice.getText().toString();
                String quantity= itemQuantity.getText().toString();
               record_item recordItem = new record_item(key,item,price,quantity);
               databaseReference.child(key).setValue(recordItem);

            }
        });
        dateandtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
               int day = calendar.get(Calendar.DATE);
               int month = calendar.get(Calendar.MONTH);
               int year = calendar.get(Calendar.YEAR);
                String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
               DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                       dateandtime.setText(""+year+"/"+month+"/"+date);
                   }
               },year,month,day);
               datePickerDialog.show();
            }
        });
        return builder.create();
    }
}
