package com.example.universityapp.adds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.universityapp.R;
import com.example.universityapp.lists.GroupListActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import com.example.universityapp.DB.DBGroups;
import com.example.universityapp.DB.DBStudents;
import com.example.universityapp.Entities.Group;

public class NewGroupActivity extends AppCompatActivity {
    Button addGroupBtn, cancelBtn;
    private EditText etFacultyName, etGroupNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        Objects.requireNonNull(getSupportActionBar()).hide();
        etFacultyName = findViewById(R.id.act_new_gr_nameOfFaculty_editText);
        etGroupNumber = findViewById(R.id.act_new_gr_group_number_editText);
        addGroupBtn = findViewById(R.id.act_new_gr_add_group_btn);
        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGroup();
            }
        });
        cancelBtn = findViewById(R.id.act_new_gr_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void addGroup(){
        String faculty = etFacultyName.getText().toString().trim();
        String groupNumber = etGroupNumber.getText().toString().trim();
        DBGroups dbHelper = new DBGroups(this);

        if(faculty.isEmpty() || groupNumber.isEmpty()) {
            Snackbar.make(findViewById(R.id.addGroup_root),"Please enter all fields!",Snackbar.LENGTH_LONG).show();
        } else {
            Group group = new Group(faculty, groupNumber);
            dbHelper.saveGroup(group);
            startActivity(new Intent(NewGroupActivity.this, GroupListActivity.class));
        }
    }
}