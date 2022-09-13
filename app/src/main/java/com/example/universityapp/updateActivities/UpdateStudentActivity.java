package com.example.universityapp.updateActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.universityapp.DB.DBStudents;
import com.example.universityapp.Entities.Student;
import com.example.universityapp.R;
import com.example.universityapp.lists.StudentListActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class UpdateStudentActivity extends AppCompatActivity {
    Button updateStudentBtn, cancelBtn;
    private EditText etFirstName, etSecondName, etPatronymic, etDateOfBirth, etGroupID;
    private long studentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        Objects.requireNonNull(getSupportActionBar()).hide();
        etFirstName = (EditText)findViewById(R.id.update_first_name_editText);
        etSecondName = (EditText)findViewById(R.id.update_second_name_editText);
        etPatronymic = (EditText)findViewById(R.id.update_patronymic_editText);
        etDateOfBirth = (EditText)findViewById(R.id.update_date_of_birth_editText);
        etGroupID = (EditText)findViewById(R.id.update_group_number_editText);
        DBStudents dbHelper = new DBStudents(this);
        try {
            studentID = getIntent().getLongExtra("USERID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Student queriedStudent = dbHelper.getStudent(studentID);
        etFirstName.setText(queriedStudent.getFirstName());
        etSecondName.setText(queriedStudent.getSecondName());
        etPatronymic.setText(queriedStudent.getPatronymic());
        etDateOfBirth.setText(queriedStudent.getDateOfBirth());
        etGroupID.setText(queriedStudent.getGroupID());

        updateStudentBtn = (Button)findViewById(R.id.update_student_btn);
        cancelBtn = (Button)findViewById(R.id.ac_update_stud_cancel_btn);

        updateStudentBtn.setOnClickListener(view -> {
            updateStudent();
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateStudent() {
        String firstName = etFirstName.getText().toString().trim();
        String secondName = etSecondName.getText().toString().trim();
        String patronymic = etPatronymic.getText().toString().trim();
        String dateOfBirth = etDateOfBirth.getText().toString().trim();
        String groupID = etGroupID.getText().toString().trim();
        DBStudents dbHelper = new DBStudents(this);

        if(firstName.isEmpty() || secondName.isEmpty() || patronymic.isEmpty() || dateOfBirth.isEmpty()) {
            Snackbar.make(findViewById(R.id.addRoot),"Please enter all fields!",Snackbar.LENGTH_LONG).show();
        } else {
            Student student = new Student(firstName, secondName, patronymic, dateOfBirth, groupID);
            dbHelper.updateStudent(studentID, student);
            startActivity(new Intent(UpdateStudentActivity.this, StudentListActivity.class));
        }
    }
}