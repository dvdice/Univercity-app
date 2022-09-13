package com.example.universityapp.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.universityapp.MainActivity;
import com.example.universityapp.R;
import com.example.universityapp.adapters.StudentAdapter;
import com.example.universityapp.adds.NewStudentActivity;

import java.util.Objects;

import com.example.universityapp.DB.DBStudents;

public class StudentListActivity extends AppCompatActivity {
    DBStudents studDBConnector;
    Context studContext;
    private RecyclerView mRecyclerView;
    StudentAdapter myAdapter;
    private String filter = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button addStudentButton = findViewById(R.id.act_stud_list_add_btn);
        Button cancelButton = findViewById(R.id.act_stud_list_cancel_btn);
        mRecyclerView = findViewById(R.id.act_stud_list_recyclerV);
        studContext=this;
        studDBConnector=new DBStudents(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        filterRecyclerList(filter);
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentListActivity.this, NewStudentActivity.class);
                startActivity(intent);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(StudentListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
    private void filterRecyclerList(String filter){
        DBStudents dbStudents = new DBStudents(this);
        myAdapter = new StudentAdapter(dbStudents.studentList(filter), this, mRecyclerView);
        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAdapter.notifyDataSetChanged();
    }

}