package com.example.universityapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.universityapp.Entities.Student;

public class DBStudents extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "University2";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "students";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_SECOND_NAME = "second_name";
    public static final String COLUMN_PATRONYMIC = "patronymic";
    public static final String COLUMN_DATE_OF_BIRTH = "dateOfBirth";
    public static final String COLUMN_GROUP_ID = "groupID";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_FIRST_NAME = 1;
    private static final int NUM_COLUMN_SECOND_NAME = 2;
    private static final int NUM_COLUMN_PATRONYMIC = 3;
    private static final int NUM_COLUMN_DATE_OF_BIRTH = 4;
    private static final int NUM_COLUMN_GROUP_ID = 4;

    private SQLiteDatabase studDataBase;

    public DBStudents(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void saveStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, student.getFirstName());
        values.put(COLUMN_SECOND_NAME, student.getSecondName());
        values.put(COLUMN_PATRONYMIC, student.getPatronymic());
        values.put(COLUMN_DATE_OF_BIRTH, student.getDateOfBirth());
        values.put(COLUMN_GROUP_ID, student.getGroupID());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Student> studentList(String filter) {
        String query;
        if (filter.equals(""))
            query = "SELECT * FROM " + TABLE_NAME;
        else
            query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + filter;

        List<Student> studentLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Student student;

        if (cursor.moveToFirst()) {
            do {
                student = new Student();
                student.set_id(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                student.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)));
                student.setSecondName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SECOND_NAME)));
                student.setPatronymic(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATRONYMIC)));
                student.setDateOfBirth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_OF_BIRTH)));
                student.setGroupID(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GROUP_ID)));
                studentLinkedList.add(student);
            } while (cursor.moveToNext());
        }
        return studentLinkedList;
    }

    public Student getStudent(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        Student receivedStudent = new Student();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            receivedStudent.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)));
            receivedStudent.setSecondName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SECOND_NAME)));
            receivedStudent.setPatronymic(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATRONYMIC)));
            receivedStudent.setDateOfBirth(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_OF_BIRTH)));
            receivedStudent.setGroupID(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GROUP_ID)));
        }
        return receivedStudent;
    }

    public void deleteAll() {
        studDataBase.delete(TABLE_NAME, null, null);
    }

    public void deleteStudent(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id='" + id + "'");
    }


    public ArrayList<Student> selectAll() {
        Cursor studCursor = studDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Student> arr = new ArrayList<>();
        studCursor.moveToFirst();
        if (!studCursor.isAfterLast()) {
            do {
                long id = studCursor.getLong(NUM_COLUMN_ID);
                String first_name = studCursor.getString(NUM_COLUMN_FIRST_NAME);
                String second_name = studCursor.getString(NUM_COLUMN_SECOND_NAME);
                String patronymic = studCursor.getString(NUM_COLUMN_PATRONYMIC);
                String dateOfBirth = studCursor.getString(NUM_COLUMN_DATE_OF_BIRTH);
                String groupID = studCursor.getString(NUM_COLUMN_GROUP_ID);
                arr.add(new Student(first_name, second_name, patronymic, dateOfBirth, groupID));
            } while (studCursor.moveToNext());
        }
        return arr;
    }
    public void updateStudent(long personId, Student updatedStudent) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE  " + TABLE_NAME + " SET first_name ='" + updatedStudent.getFirstName() + "', " +
                "second_name ='" + updatedStudent.getSecondName() + "', patronymic ='" + updatedStudent.getPatronymic() + "'," +
                " dateOfBirth ='" + updatedStudent.getDateOfBirth() + "', " + " groupID ='" + updatedStudent.getGroupID()
                + "'  WHERE _id='" + personId + "'");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement,"
                + COLUMN_FIRST_NAME + " text," + COLUMN_SECOND_NAME + " text," + COLUMN_PATRONYMIC + " text,"
                + COLUMN_DATE_OF_BIRTH + " date,"+ COLUMN_GROUP_ID + " text" +")");
    }
    //+ "foreign key (" + COLUMN_GROUP_ID + ")" + " references groups(_id)"

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}