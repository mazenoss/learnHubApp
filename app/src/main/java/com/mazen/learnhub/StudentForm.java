package com.mazen.learnhub;

import android.content.Intent;
import android.os.FileUriExposedException;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class StudentForm extends AppCompatActivity implements View.OnClickListener {
    private EditText mFullName, mBirthDate, mPhoneNumber, mEmail;
    private TextInputEditText mPassword;
    private Button mRegister;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        mAuth = FirebaseAuth.getInstance();


        //initializing
        mFullName = (EditText) findViewById(R.id.txtname);
        mEmail = (EditText)  findViewById(R.id.txtmail);
        mPassword = (TextInputEditText) findViewById(R.id.txtpass);
        mPhoneNumber = (EditText)  findViewById(R.id.txtphone);
        mBirthDate = (EditText)  findViewById(R.id.date);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        findViewById(R.id.btnreg).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            // هنا نحدد المطلوب اذا كان المستخدم مسجلا من قبل
            //startActivity(new Intent(StudentForm.this, StudentForm.class));

        }
    }

    private void registerUser(){
         final String fullName = mFullName.getText().toString().trim();
         final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
         final String phone = mPhoneNumber.getText().toString().trim();
         final String birthDate = mBirthDate.getText().toString().trim();

        if (fullName.isEmpty()){
            mFullName.setError("Full name required");
            mFullName.requestFocus();
            return;
        }
        if (email.isEmpty()){
            mEmail.setError("Email address reqired");
            mEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            mPassword.setError("Choose a password");
            mPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            mPassword.setError("password is too short");
            mPassword.requestFocus();
            return;
        }
        if (phone.isEmpty()){
            mPhoneNumber.setError("Phone Number required");
            mPhoneNumber.requestFocus();
            return;
        }
        if (phone.length()!= 11){
            mPhoneNumber.setError("enter a valid phone number");
            mPhoneNumber.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Enter valid Email Address");
            mEmail.requestFocus();
            return;
        }
        if (birthDate.isEmpty()){
            mBirthDate.setError("Birth date required");
            mBirthDate.requestFocus();
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                //we store addetional fields in database
                                 User user = new User(fullName, email, phone, birthDate);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        mProgressBar.setVisibility(View.GONE);

                                        if (task.isSuccessful()){
                                            Toast.makeText(StudentForm.this,"Registration success",Toast.LENGTH_SHORT).show();
                                           //starting activity after signing up
                                           startActivity(new Intent(StudentForm.this, Courses.class));
                                        }
                                        else {
                                            //this code to know if the email is taken or not
                                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                                Toast.makeText(getApplicationContext(),"This Email is already taken", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });

                            }
                            else {
                                Toast.makeText(StudentForm.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
}
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnreg:
                registerUser();

                break;
        }

    }


}
