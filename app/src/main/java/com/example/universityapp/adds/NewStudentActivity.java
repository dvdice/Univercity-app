package com.example.universityapp.adds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.universityapp.R;
import com.example.universityapp.lists.StudentListActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import com.example.universityapp.DB.DBStudents;
import com.example.universityapp.Entities.Student;

public class NewStudentActivity extends AppCompatActivity {

    Button addStudentBtn, cancelBtn;
    private EditText etFirstName, etSecondName, etPatronymic, etDateOfBirth, etGroupID;
    private long studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);
        Objects.requireNonNull(getSupportActionBar()).hide();
        etFirstName = (EditText)findViewById(R.id.first_name_editText);
        etSecondName = (EditText)findViewById(R.id.second_name_editText);
        etPatronymic = (EditText)findViewById(R.id.patronymic_editText);
        etDateOfBirth = (EditText)findViewById(R.id.date_of_birth_editText);
        etGroupID = (EditText)findViewById(R.id.group_number_editText);

        addStudentBtn = (Button)findViewById(R.id.add_student_btn);
        cancelBtn = (Button)findViewById(R.id.ac_new_stud_cancel_btn);

        addStudentBtn.setOnClickListener(view -> {
            addStudent();
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void addStudent(){
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
            dbHelper.saveStudent(student);
            startActivity(new Intent(NewStudentActivity.this, StudentListActivity.class));
        }
    }
}