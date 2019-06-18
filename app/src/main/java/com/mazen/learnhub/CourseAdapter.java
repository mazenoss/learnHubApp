package com.mazen.learnhub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.support.v4.content.ContextCompat.startActivity;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private OnItemClickListener listener;
    //AbdOo
    private List<list> listList;

    public CourseAdapter(ArrayList<list> listList) {
        this.listList = listList;
    }

    public void filterList(List<list> filteredList) {
        listList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CourseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder courseHolder, int i) {
        //AbdOo
        final list listData = listList.get(i);

        courseHolder.txtcoursename.setText(listData.getCoursename());
        courseHolder.txtinstructorname.setText(listData.getInstructorname());
        courseHolder.txtarea.setText(listData.getArea());
        courseHolder.txthours.setText(listData.getHours());
        courseHolder.txtcost.setText(listData.getCost());

    }

    @Override
    public int getItemCount() {
        return listList.size();
    }

    class CourseHolder extends RecyclerView.ViewHolder {

        TextView txtcoursename;
        TextView txtinstructorname;
        TextView txtarea;
        TextView txthours;
        TextView txtcost;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            txtcoursename = itemView.findViewById(R.id.txtcoursename);
            txtinstructorname = itemView.findViewById(R.id.txt_instructor_name);
            txtarea = itemView.findViewById(R.id.txt_area);
            txthours = itemView.findViewById(R.id.txt_hours);
            txtcost = itemView.findViewById(R.id.txt_cost);

            //to click on a cardView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
//                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }


    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemCllickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
