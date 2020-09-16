package com.example.invoicerecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText firstName,lastName,deleteText;
    Button record,retrieve,deletebyID,showFragment,buttonFragment;
    ListView listView;

    ArrayList<record_item> record_itemArraylist = new ArrayList<>();;

    //initiate firebase
   // final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find edit text
        deleteText=findViewById(R.id.EditTxtId);
        //find button
        record = findViewById(R.id.btnRecord);
        showFragment = findViewById(R.id.recordItem);
        retrieve = findViewById(R.id.btnRetrieve);
        deletebyID = findViewById(R.id.btnDelete);
        //find listview
        listView = findViewById(R.id.listView);
        //custom spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapterForSpinner = ArrayAdapter.createFromResource(this,R.array.actions,android.R.layout.simple_spinner_item);
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterForSpinner);
        spinner.setOnItemSelectedListener(this);
        //spinner.setOnItemSelectedListener(this);
        //custom listview
        final recorditemAdapter adapter = new recorditemAdapter(this,R.layout.listview_adapter,record_itemArraylist);
        listView.setAdapter(adapter);

        //Fragment to add
      showFragment.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              openDialog();
          }
      });

        //button action
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               databaseReference = FirebaseDatabase.getInstance().getReference().child("invoice").child("item");
                String key= databaseReference.push().getKey();
                //get data from edit text
                //String firstname="helo";
                //String lastname="hi";

                //record_item recordItem= new record_item(firstname,lastname);

                //databaseReference.child(key).setValue(recordItem);

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

                       //test function

                         record_item item1= new record_item(firstname,lastname);
                         record_itemArraylist.add(item1);
                         System.out.println(record_itemArraylist);
                         adapter.notifyDataSetChanged();

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
                      //  String rmID = deleteText.getText().toString();
                      //  //databaseReference.child(rmID).removeValue();
                      //  System.out.println(rmID);
                      //  System.out.println("button click");
                      //  databaseReference.child(rmID).removeValue();


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });//end of delete button
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //  Object object = listView.getAdapter().getItem(i);
              //  String value = object.toString();
              //  System.out.println(value);
                String name =((TextView) view.findViewById(R.id.TxtViewItem)).getText().toString();
                String price =((TextView) view.findViewById(R.id.TxtViewPrice)).getText().toString();
                System.out.println(name+price);

                Toast.makeText(getApplicationContext(),"you selected "+ name+price,Toast.LENGTH_LONG).show();
            }
        });
        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        //ListView
        //retrieve data from firebase

        //Object to store data
            //record_item item1= new record_item("hhh","dfsd");

        //Add object to Arraylist
            //ArrayList<record_item> record_itemArraylist= new ArrayList<>();
            //record_itemArraylist.add(item1);


        //custom class for listview
        //recorditemAdapter adapter = new recorditemAdapter(this,R.layout.listview_adapter,record_itemArraylist);
        //listView.setAdapter(adapter);
    }//do not delete this one
    public void openDialog(){
        record_item_fragment recordItemFragment = new record_item_fragment();
        recordItemFragment.show(getSupportFragmentManager(),"Input data");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, "text"+text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}