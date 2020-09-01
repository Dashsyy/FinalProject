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
            DatabaseReference postsRef = ref.child("item");
            DatabaseReference newPost = postsRef.push();

            //get data
                String firstname = firstName.getText().toString();
                String lastname  = lastName.getText().toString();

                record_item recordItem= new record_item(firstname,lastname);

                newPost.setValue(recordItem);

            }
        });
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String postid = dataSnapshot.getKey();
                     //  String fn= dataSnapshot.child("firstName").getValue().toString();
                     //  String ln= dataSnapshot.child("lastName").getValue().toString();
                     // record_item recordItem = new record_item(fn,ln);
                        System.out.println(postid);
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