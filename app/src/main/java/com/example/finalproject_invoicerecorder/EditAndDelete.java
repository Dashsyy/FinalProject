package com.example.finalproject_invoicerecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditAndDelete extends AppCompatActivity {
    String name,price,uID;
    String updateItem,updatePrice,updateitemQuantity;
    EditText editItem,editPrice,editeQuantity;
    EditText editItemUpdate,editPriceUpdate,editQuantityUpdate;
    Button buttonDeleteById,buttonUpdateById;

    //create object to update
    record_item record_item;
    //initiate firebase
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete);

        Intent intent=getIntent();
        //get data from MainActivity
        uID = intent.getStringExtra("uID");
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        getSupportActionBar().setTitle("Edit");

        //find edittext
        editItem = findViewById(R.id.EditTxtItem1);
        editPrice = findViewById(R.id.EditTxtPrice1);
        editeQuantity = findViewById(R.id.EditTxtQuantity1);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("invoice").child("item").child(uID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String quantity = snapshot.child("quantity").getValue().toString();
                editeQuantity.setText(quantity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //set text to edittext
        editItem.setText(name);
        editPrice.setText(price);
        //Number 1 at the end are for update in xml.
        editItemUpdate = (EditText)findViewById(R.id.EditTxtItem1);
        editPriceUpdate = (EditText)findViewById(R.id.EditTxtPrice1);
        editQuantityUpdate = (EditText)findViewById(R.id.EditTxtQuantity1);

        //FindViewById button
        buttonDeleteById = findViewById(R.id.btnDelete);
        buttonUpdateById = findViewById(R.id.btnUpdate);

        //Action
        //Delete button
        buttonDeleteById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("invoice").child("item");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(uID).removeValue();
                        Toast.makeText(EditAndDelete.this,"Delete sucessfully",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Intent intent = new Intent(EditAndDelete.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Update button
        buttonUpdateById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("invoice").child("item");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //perform action
                        updateItem = editItemUpdate.getText().toString();
                        updatePrice = editPriceUpdate.getText().toString();
                        updateitemQuantity = editQuantityUpdate.getText().toString();
                        record_item = new record_item(uID,updateItem,updatePrice,updateitemQuantity);
                        databaseReference.child(uID).setValue(record_item);
                        Intent intent = new Intent(EditAndDelete.this,MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(EditAndDelete.this,"Update sucessfully",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                finish();
            }
        });
    }
}