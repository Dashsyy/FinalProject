package com.example.invoicerecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText firstName,lastName;
    Button record,retrieve;

    //initiate firebase
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("invoice");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.EditTxtfn);
        lastName = findViewById(R.id.EditTxtln);

        record = findViewById(R.id.btnRecord);
        retrieve = findViewById(R.id.btnRetrieve);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key= ref.push().getKey();
                System.out.println(key);


                //get data from edit text
                String firstname = firstName.getText().toString();
                String lastname  = lastName.getText().toString();


                record_item recordItem= new record_item(firstname,lastname);

                ref.child(key).setValue(recordItem);

            }
        });
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set path for database to retrieve
                ref= FirebaseDatabase.getInstance().getReference().child("invoice").child("item");
                ref.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //record_item recordItem = dataSnapshot.getValue(record_item.class);
                   for (DataSnapshot chilSnapshot : dataSnapshot.getChildren()){
                       String getkey = chilSnapshot.getKey();

                       String firstname = dataSnapshot.child(getkey).child("firstname").getValue().toString();
                       String lastname = dataSnapshot.child(getkey).child("lastname").getValue().toString();
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
    }
}