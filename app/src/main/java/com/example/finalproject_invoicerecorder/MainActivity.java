package com.example.finalproject_invoicerecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject_invoicerecorder.record_item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button showFragment;
    TextView dateandtime,userName;
    ListView listView;
    ArrayList<record_item> record_itemArraylist = new ArrayList<>();;
    DatabaseReference databaseReference;
    //
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initiate
        dateandtime = (TextView) findViewById(R.id.btnDateAndTime);
        showFragment = findViewById(R.id.recordItem);
        listView = findViewById(R.id.listView);
        userName = findViewById(R.id.TxtUsername);
        //set adapter for everyone
        final recorditemAdapter adapter = new recorditemAdapter(this,R.layout.listview_adapter,record_itemArraylist);
        listView.setAdapter(adapter);

        //get user profile and username after login(only user name first)
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //storeageReference = FirebaseStorage.getInstance().getReference();
        //StorageReference profileRef = storeageReference.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        databaseReference = firebaseDatabase.getReference("user");
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String username= "" + dataSnapshot.child("fname").getValue();

                    userName.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //get data after login
        databaseReference = FirebaseDatabase.getInstance().getReference().child("invoice").child("item");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chilsnapshot: snapshot.getChildren()){
                    String getkey = chilsnapshot.getKey();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("invoice").child("item").child(getkey);

                    String item = snapshot.child(getkey).child("item").getValue().toString();
                    String price = snapshot.child(getkey).child("price").getValue().toString();

                    //set to object
                    record_item recordItem = new record_item(getkey,item,price);
                    record_itemArraylist.add(recordItem);
                    adapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Fragment to add
      showFragment.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              //you can find this function at the bottom
              openDialog();

          }
      });

        //button action
        // TODO something here
        //end of button action

        //Listview send data to another activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this,EditAndDelete.class);
                String uID = ((TextView) view.findViewById(R.id.TxtViewId)).getText().toString();
                String name =((TextView) view.findViewById(R.id.TxtViewItem)).getText().toString();
                String price =((TextView) view.findViewById(R.id.TxtViewPrice)).getText().toString();

                //putExtra
                intent.putExtra("uID",uID);
                intent.putExtra("name",name);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });

    }//do not delete this one


    //function to open record fragment
    public void openDialog(){
        record_item_fragment recordItemFragment = new record_item_fragment();
        recordItemFragment.show(getSupportFragmentManager(),"Input data");
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.YEAR,year);
        calendar.set(calendar.MONTH,month);
        calendar.set(calendar.DATE,date);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    }
}