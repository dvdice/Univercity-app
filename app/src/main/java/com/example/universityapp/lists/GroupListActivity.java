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
import com.example.universityapp.adapters.GroupAdapter;
import com.example.universityapp.adds.NewGroupActivity;

import java.util.Objects;

import com.example.universityapp.DB.DBGroups;
import com.example.universityapp.DB.DBStudents;

public class GroupListActivity extends AppCompatActivity {
    private RecyclerView recycler;
    DBGroups groupDBConnector;
    Context grContext;
    GroupAdapter myAdapter;
    private String filter = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        recycler = findViewById(R.id.act_gr_list_recycler);
        Button addBtn = findViewById(R.id.act_gr_list_add_btn);
        grContext = this;
        groupDBConnector = new DBGroups(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(mLayoutManager);
        filterRecyclerList(filter);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupListActivity.this, NewGroupActivity.class);
                startActivity(intent);
            }
        });
        Button cancelBtn = findViewById(R.id.act_gr_list_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(GroupListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void filterRecyclerList(String filter){
        DBGroups dbGroups = new DBGroups(this);
        myAdapter = new GroupAdapter(dbGroups.groupList(filter), this, recycler);
        recycler.setAdapter(myAdapter);
    }
}