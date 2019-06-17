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

public class CourseAdapter extends FirestoreRecyclerAdapter<list, CourseAdapter.CourseHolder> {
private OnItemClickListener listener;
//AbdOo
    private ArrayList<list> listList;

    public CourseAdapter(@NonNull FirestoreRecyclerOptions<list> options, ArrayList<list> listList) {
        super(options);
        this.listList = listList;
    }

    public CourseAdapter(@NonNull FirestoreRecyclerOptions<list> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull CourseHolder holder, int position, @NonNull list model) {

        //AbdOo
        final list listData = listList.get(position);
        holder.txtcoursename.setText(listData.getCoursename());
        holder.txtinstructorname.setText(listData.getInstructorname());
        holder.txtarea.setText(listData.getArea());
        holder.txthours.setText(listData.getHours());
        holder.txtcost.setText(listData.getCost());

        /*
        holder.txtcoursename.setText(model.getCoursename());
        holder.txtinstructorname.setText(model.getInstructorname());
        holder.txtarea.setText(model.getArea());
        holder.txthours.setText(model.getHours());
        holder.txtcost.setText(model.getCost());
        */


    }

    public void filterList(List<list> filteredList) {
        //clintArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CourseHolder(v);
    }

    class  CourseHolder extends RecyclerView.ViewHolder {

        TextView txtcoursename;
        TextView txtinstructorname;
        TextView txtarea;
        TextView txthours;
        TextView txtcost;
        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            txtcoursename = (TextView) itemView.findViewById(R.id.txtcoursename);
            txtinstructorname = (TextView) itemView.findViewById(R.id.txt_instructor_name);
            txtarea = (TextView) itemView.findViewById(R.id.txt_area);
            txthours = (TextView) itemView.findViewById(R.id.txt_hours);
            txtcost = (TextView) itemView.findViewById(R.id.txt_cost);

            //to click on a cardView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }


    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemCllickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
