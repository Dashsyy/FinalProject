package com.example.finalproject_invoicerecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLogin;
    TextView tRegister, tRecover;
    FirebaseAuth fAuth;
    String email;
    ProgressDialog pd;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.editTextEmail);
        mPassword = findViewById(R.id.editTextPassword);
        mLogin = findViewById(R.id.LoginBtn);
        tRegister = findViewById(R.id.registerText);
        tRecover = findViewById(R.id.forgetpassTxt);
        fAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();


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

                //
                //Send email to mainactivity

                //Authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            intent.putExtra("email",encodeUderEmail(email));
                            System.out.println(encodeUderEmail(email)+" In login");
                            Toast.makeText(Login.this, "Logged in Succesfully ", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }else {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        //go to Create User Account
        tRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        //recover password
        tRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecoverPasswordDiaolog();
            }
        });


    }

    private void showRecoverPasswordDiaolog() {
        //AlertDialog
        AlertDialog.Builder bulider = new AlertDialog.Builder(this);
        bulider.setTitle("Recover Password");
        //set layout linaer layout
        LinearLayout linearLayout = new LinearLayout(this);
        //views to set in dailog
        final EditText emailEt = new EditText(this);
        emailEt.setHint("Email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEt.setMinEms(20);

        linearLayout.addView(emailEt);
        linearLayout.setPadding(10,10,10,10);

        bulider.setView(linearLayout);

        //buttons recover
        bulider.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //input email
                String eemail = emailEt.getText().toString().trim();
                if(TextUtils.isEmpty(eemail)){
                    Toast.makeText(Login.this,"Please Fill Your Email Address",Toast.LENGTH_SHORT).show();
                    return;
                }
                beginRecovery(eemail);
            }
        });
        //buttons cancel
        bulider.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dismiss dialog
                dialogInterface.dismiss();
            }
        });
        //show dialog
        bulider.create().show();
    }

    private void beginRecovery( String eemail) {
        //show progress dialogy
        pd.setMessage("Sending email...");
        pd.show();
        fAuth.sendPasswordResetEmail(eemail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(Login.this,"Email sent",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Login.this,"Can't find user account",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Login.this,"",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String encodeUderEmail(String userEmail){
        return userEmail.replace(".",",");
    }
}