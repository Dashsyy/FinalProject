package com.example.invoicerecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    EditText firstName,lastName,deleteText;
    Button record,retrieve,deletebyID;

    //initiate firebase
   // final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteText=findViewById(R.id.EditTxtId);
        firstName = findViewById(R.id.EditTxtfn);
        lastName = findViewById(R.id.EditTxtln);

        record = findViewById(R.id.btnRecord);
        retrieve = findViewById(R.id.btnRetrieve);
        deletebyID = findViewById(R.id.btnDelete);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               databaseReference = FirebaseDatabase.getInstance().getReference().child("invoice").child("item");
                String key= databaseReference.push().getKey();
                System.out.println(key);

                //get data from edit text
                String firstname = firstName.getText().toString();
                String lastname  = lastName.getText().toString();

                record_item recordItem= new record_item(firstname,lastname);

                databaseReference.child(key).setValue(recordItem);

            }
        });//end of add button
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set path for database to retrieve
                databaseReference = FirebaseDatabase.getInstance().getReference().child("invoice").child("item");
                databaseReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //record_item recordItem = dataSnapshot.getValue(record_item.class);
                     for (DataSnapshot chilSnapshot : dataSnapshot.getChildren()){
                       String getkey = chilSnapshot.getKey();
                       databaseReference= FirebaseDatabase.getInstance().getReference().child("invoice").child("item").child(getkey);

                       String firstname = dataSnapshot.child(getkey).child("firstName").getValue().toString();
                       String lastname = dataSnapshot.child(getkey).child("lastName").getValue().toString();
                       System.out.println(firstname+lastname);

                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

            }
        });
        //end of button
        deletebyID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemp delete by id by input(not goint to do this way just to test) later on we
                //get id by select on listview and get id auto to put in the rmID.
                databaseReference = FirebaseDatabase.getInstance().getReference().child("invoice").child("item");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String rmID = deleteText.getText().toString();
                        //databaseReference.child(rmID).removeValue();
                        System.out.println(rmID);
                        System.out.println("button click");
                        databaseReference.child(rmID).removeValue();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });//end of delete button
    }//do not delete this one
}