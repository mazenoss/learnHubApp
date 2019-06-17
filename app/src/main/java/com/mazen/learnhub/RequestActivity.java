package com.mazen.learnhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestActivity extends AppCompatActivity {
    EditText courseName;
    Spinner category, area;
    Button add;
    DatabaseReference databaseCourseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        databaseCourseRequest = FirebaseDatabase.getInstance().getReference("course_request")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        courseName = (EditText)  findViewById(R.id.txtcoursename);
        category = (Spinner) findViewById(R.id.spncategory);
        area = (Spinner) findViewById(R.id.spnarea);
        add = (Button) findViewById(R.id.btnreq);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourse();
                startActivity(new Intent(RequestActivity.this, Courses.class));

            }
        });
    }
    private void addCourse(){
        String name = courseName.getText().toString().trim();
        String categories = category.getSelectedItem().toString().trim();
        String areas = area.getSelectedItem().toString().trim();

        if (name.isEmpty()){
            courseName.setError("Enter course name");
            courseName.requestFocus();
            return;
        }

        if (!TextUtils.isEmpty(name)){
            String id = databaseCourseRequest.push().getKey();
            CourseAdding courseAdding = new CourseAdding(id, name,categories,areas);
            databaseCourseRequest.child(id).setValue(courseAdding);
            Toast.makeText(this, "Course added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "please fill the field", Toast.LENGTH_SHORT).show();
        }
    }
    // menu codes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.language:
                //todo: change the layout language
                return true;
            case R.id.student:
                startActivity(new Intent(RequestActivity.this, RequestActivity.class));
                return true;
            case R.id.instructor:
                startActivity(new Intent(RequestActivity.this, InstructorActivity.class));
                return true;
            case R.id.contact:
                // TODO: code to enter contact us page
                return true;
            case R.id.sign_out_menu:
                //TODO: sign out
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(RequestActivity.this, Signin.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
