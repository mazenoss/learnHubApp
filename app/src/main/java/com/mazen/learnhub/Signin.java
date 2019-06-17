package com.mazen.learnhub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.design.widget.TextInputLayout;
import android.support.design.widget.TextInputEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;



public class Signin extends AppCompatActivity implements View.OnClickListener {

    EditText mEmail;
    TextInputEditText mPass;
    ProgressBar mBar;
    FirebaseAuth mAuth;
    private View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        // save login data
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
          mAuth.getCurrentUser().toString();
            startActivity(new Intent(Signin.this, Courses.class));
            finish();
        }


        mAuth = FirebaseAuth.getInstance();
       mEmail = (EditText)  findViewById(R.id.txtmail);
      mPass = findViewById(R.id.txtpass);
       mBar = (ProgressBar) findViewById(R.id.progressBar);
        mBar.setVisibility(View.GONE);
        findViewById(R.id.btnsignin).setOnClickListener(this);










    }




@RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
private void loginuser(){

        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString().trim();

    if (email.isEmpty()){
        mEmail.setError("Email address reqired");
        mEmail.requestFocus();
        return;
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        mEmail.setError("Enter valid Email Address");
        mEmail.requestFocus();
        return;
    }
    if (pass.isEmpty()){
        mPass.setError("Choose a password");
        mPass.requestFocus();
        return;
    }
    if (pass.length()<6){
        mPass.setError("password is too short");
        mPass.requestFocus();
        return;
    }
        mBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Toast.makeText(Signin.this,"Successful login",Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(Signin.this, Courses.class));
                 finish();

                }
                else {
                    //this code to know if the email is taken or not
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){

                    }else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

}



    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnsignin:
                loginuser();

                break;
        }
    }
    public void signin(View view) {
        startActivity(new Intent(Signin.this, StudentForm.class));
    }



  //exit from the app while press back button
    public void onBackPressed() {
        finishAffinity();
    }


    public void forget(View view) {
        startActivity(new Intent(Signin.this, ResetPasswordActivity.class));
    }
}
