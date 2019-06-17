package com.mazen.learnhub;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {


    EditText resetPassword;
    Button resetbtn;
    ProgressBar resetBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

      resetPassword =findViewById(R.id.emailreset);
       resetBar = findViewById(R.id.resetbar);
       resetbtn = findViewById(R.id.resetbtn);
        resetBar.setVisibility(View.GONE);

        firebaseAuth = firebaseAuth.getInstance();


        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetBar.setVisibility(View.VISIBLE);



                firebaseAuth.sendPasswordResetEmail(resetPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        resetBar.setVisibility(View.GONE);

                        if (task.isSuccessful()){
                            Toast.makeText(ResetPasswordActivity.this,"Password sent to your Email",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ResetPasswordActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }
}
