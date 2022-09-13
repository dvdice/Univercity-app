package com.example.universityapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import com.example.universityapp.Entities.Group;

public class DBGroups extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "University1";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "groups";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME_OF_FACULTY = "name_of_faculty";
    public static final String COLUMN_NUMBER_OF_GROUP = "number_of_group";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME_OF_FACULTY = 1;

    private SQLiteDatabase groupDataBase;

    public DBGroups( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void saveGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_OF_FACULTY, group.getNameOfFaculty());
        values.put(COLUMN_NUMBER_OF_GROUP, group.getGroupNumber());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void deleteGroup(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id='" + id + "'");
    }

    public List<Group> groupList(String filter) {
        String query;
        if (filter.equals(""))
            query = "SELECT * FROM " + TABLE_NAME;
        else
            query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + filter;

        List<Group> groupLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Group group;

        if (cursor.moveToFirst()) {
            do {
                group = new Group();
                group.setGroupNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER_OF_GROUP)));
                group.set_id(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                group.setNameOfFaculty(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_OF_FACULTY)));
                groupLinkedList.add(group);
            } while (cursor.moveToNext());
        }
        return groupLinkedList;
    }

    public void updateGroup(long groupId, Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE  " + TABLE_NAME + " SET name_of_faculty ='" + group.getNameOfFaculty() + "', " +
                "number_of_group ='" + group.getGroupNumber()
                + "'  WHERE _id='" + groupId + "'");
    }

    public Group getGroup(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        Group receivedGroup = new Group();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            receivedGroup.setNameOfFaculty(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_OF_FACULTY)));
            receivedGroup.setGroupNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER_OF_GROUP)));
        }
        return receivedGroup;
    }

    public void deleteAll() {
        groupDataBase.delete(TABLE_NAME, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement,"
                + COLUMN_NAME_OF_FACULTY + " text," + COLUMN_NUMBER_OF_GROUP + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
