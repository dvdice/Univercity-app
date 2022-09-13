package com.example.universityapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.R;
import com.example.universityapp.updateActivities.UpdateStudentActivity;

import java.util.List;

import com.example.universityapp.DB.DBStudents;
import com.example.universityapp.Entities.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> studentList;
    private Context context;
    private RecyclerView recyclerView;

    public StudentAdapter(List<Student> myDataset, Context context, RecyclerView recyclerView) {
        this.studentList = myDataset;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_student, parent, false);//!!!!!!!!!!!!!!!!!!!!!!!!!
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Student student = studentList.get(position);
        holder.firstName.setText(String.valueOf(student.getFirstName()));
        holder.secondName.setText(String.valueOf(student.getSecondName()));
        holder.patronymic.setText(String.valueOf(student.getPatronymic()));
        holder.dateOfBirth.setText(String.valueOf(student.getDateOfBirth()));
        holder.groupID.setText(String.valueOf(student.getGroupID()));

        Runnable r = ()->{
            holder.view.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Выбрать");
                builder.setMessage("Удалить или изменить?");
                builder.setPositiveButton("Изменить", (dialog, which) -> {
                    goToUpdateActivity(student.get_id());
                });
                builder.setNeutralButton("Удалить", (dialog, which) -> {
                    DBStudents dbHelper = new DBStudents(context);
                    dbHelper.deleteStudent(student.get_id());
                    studentList.remove(position);
                    recyclerView.removeViewAt(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, studentList.size());
                    notifyDataSetChanged();
                });
                builder.setNegativeButton(R.string.cancel_str, (dialog, which) -> dialog.dismiss());
                builder.create().show();
            });
        };
        Thread myThread = new Thread(r,"MyThread");
        myThread.start();

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    private void goToUpdateActivity(long personId){
        Intent goToUpdate = new Intent(context, UpdateStudentActivity.class);
        goToUpdate.putExtra("USERID", personId);
        context.startActivity(goToUpdate);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstName, secondName, patronymic, dateOfBirth, groupID;
        public View view;
        public ViewHolder(View v) {
            super(v);
            this.view = v;
            firstName = (TextView) v.findViewById(R.id.firstName);
            secondName = (TextView) v.findViewById(R.id.secondName);
            patronymic =(TextView) v.findViewById(R.id.patronymic);
            dateOfBirth = (TextView)v.findViewById(R.id.dateOfBirth);
            groupID = (TextView)v.findViewById(R.id.groupID);
        }
    }
}
