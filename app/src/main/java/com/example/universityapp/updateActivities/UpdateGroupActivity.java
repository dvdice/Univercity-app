package com.example.universityapp.updateActivities;

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
import com.example.universityapp.Entities.Group;

public class UpdateGroupActivity extends AppCompatActivity {
    Button updateBtn, cancelBtn;
    private EditText etFaculty, etGroupNum;
    private long groupID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_update_group);
        etFaculty = (EditText)findViewById(R.id.act_update_gr_faculty);
        etGroupNum = (EditText)findViewById(R.id.update_groupN_editText);
        DBGroups dbHelper = new DBGroups(this);
        try {
            groupID = getIntent().getLongExtra("GROUPID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Group group = dbHelper.getGroup(groupID);
        etFaculty.setText(group.getNameOfFaculty());
        etGroupNum.setText(group.getGroupNumber());

        updateBtn = (Button)findViewById(R.id.update_group_btn);
        cancelBtn = (Button)findViewById(R.id.ac_update_gr_cancel_btn);

        updateBtn.setOnClickListener(view -> {
            updateGroup();
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateGroup() {
        String groupNum = etGroupNum.getText().toString().trim();
        String facultyName = etFaculty.getText().toString().trim();
        DBGroups dbHelper = new DBGroups(this);

        if(groupNum.isEmpty() || facultyName.isEmpty()) {
            Snackbar.make(findViewById(R.id.addRoot),"Please enter all fields!",Snackbar.LENGTH_LONG).show();
        } else {
            Group group = new Group(groupNum, facultyName);
            dbHelper.updateGroup(groupID, group);
            startActivity(new Intent(UpdateGroupActivity.this, GroupListActivity.class));
        }
    }
}