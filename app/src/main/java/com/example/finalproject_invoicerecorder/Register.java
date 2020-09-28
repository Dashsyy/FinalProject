package com.example.finalproject_invoicerecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText mFirstname,mLastname,mPassword,mEmail,mPhone;
    Button createBtn;
    TextView mCancel;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirstname = findViewById(R.id.firstname);
        mLastname = findViewById(R.id.lastname);
        mPassword = findViewById(R.id.EditTextPass);
        mEmail = findViewById(R.id.EdittextEmail);
        mPhone = findViewById(R.id.EditTextPhone);
        createBtn = findViewById(R.id.registerBtn);
        mCancel = findViewById(R.id.cancelText);

        fAuth = FirebaseAuth.getInstance();


        if(fAuth.getCurrentUser() == null ){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get all value
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fname = mFirstname.getText().toString().trim();
                String lname = mLastname.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();

                if(TextUtils.isEmpty(fname)){
                    mFirstname.setError("First Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(lname)){
                    mLastname.setError("Last Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    mPhone.setError("Phone Number is Required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if(password.length() < 6 ){
                    mPassword.setError("Password Must be >= 6 character or number");
                    return;
                }
                //method to register email and password in Aunthantic firebase
                beginRegister(email,password,fname,lname,phone);
              /*  //method to store user profile in realtime database
                beginInsert(email,password,fname,lname,phone);*/
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    private void beginRegister(final String email, final String password, final String fname, final String lname, final String phone) {
        //register email and password in firebase
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                    beginInsert(email,password,fname,lname,phone);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void beginInsert(String email, String password, String fname, String lname, String phone) {
        //Connect and get reference from real time database
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user");
        String key = reference.push().getKey();
        System.out.println(key);
        //Constructtor
        UserHelperClass helperClass = new UserHelperClass();
        helperClass = new UserHelperClass(fname,lname,email,phone,password);
        //add to firebase
        reference.child(key).setValue(helperClass);
    }
}