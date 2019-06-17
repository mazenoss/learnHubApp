package com.mazen.learnhub;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Courses extends AppCompatActivity {

private FirebaseFirestore db = FirebaseFirestore.getInstance();
private CollectionReference courseRef = db.collection("courses");


private CourseAdapter adapter;

//AbdOo
private ArrayList<list> list = new ArrayList<>();

//reserve
    DatabaseReference databaseReserve;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        //reserve
        databaseReserve = FirebaseDatabase.getInstance().getReference("course_reserve")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        setUpRecyclerView();
    }


    //to view the cardView
private void setUpRecyclerView(){
        Query query = courseRef.orderBy("coursename", Query.Direction.ASCENDING);

        //AbdOo
    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    list tp = document.toObject(list.class);

                 //   list encap3 = document.getData(list.class);

                    list.add(tp);
                    Log.i("AbdOo",list+"");
                }
            }
        }
    });

    //////



        FirestoreRecyclerOptions<list> options = new FirestoreRecyclerOptions.Builder<list>()
                .setQuery(query,list.class).build();

        adapter = new CourseAdapter(options,list);
        RecyclerView recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //item clickListener for card view
        adapter.setOnItemCllickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                String cid = databaseReserve.push().getKey();
                String id = documentSnapshot.getId();
                databaseReserve.child(cid).setValue(id);
                Toast.makeText(Courses.this, "Your reservation is accepted!",Toast.LENGTH_LONG).show();




            }
        });

    }




    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    // menu codes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_ic);
       // SearchView searchView = (SearchView) searchItem.getActionView();
        ////searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           // @Override
          ///  public boolean onQueryTextSubmit(String query) {
          //      return false;
        return false;
    }
        //    @Override
          //  public boolean onQueryTextChange(String newText) {
           //     if (newText == null || newText.isEmpty()) {
             //       filter("");
           ///     } else {
             ///       filter(newText.trim());
             //   }
            //    return false;
            //}
       // });
       // return true;
        //return super.onCreateOptionsMenu(menu);
    //}
    private void filter(String text) {
        List<list> filteredList = new ArrayList<>();
      //  //for (list item : clintArrayList) {
          //  if (item.getCoursename().toLowerCase().contains(text.toLowerCase())) {
           //     filteredList.add(item);
          //  }
        //}
      //  viewAdapter.filterList(filteredList);
    }



    /* @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         // Handle item selection
         switch (item.getItemId()) {

             case R.id.language:
                 //todo: change the layout language
                 return true;
             case R.id.student:
                 startActivity(new Intent(Courses.this, RequestActivity.class));
                 return true;
             case R.id.instructor:
                 startActivity(new Intent(Courses.this, InstructorActivity.class));
                 return true;
             case R.id.contact:
                 // TODO: code to enter contact us page
                 return true;
             case R.id.sign_out_menu:
 
                 FirebaseAuth.getInstance().signOut();
                 startActivity(new Intent(Courses.this, Signin.class));
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }
     }*/
    public void onBackPressed() {
        finishAffinity();
    }



}
