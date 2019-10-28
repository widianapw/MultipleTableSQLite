package com.example.multipletablesqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.multipletablesqlite.model.Prodi;
import com.example.multipletablesqlite.model.Student;
import com.example.multipletablesqlite.util.Config;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseQueryClass {
    private Context context;

    public DatabaseQueryClass(Context context) {
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertStudent(Student student, int idProdi) {
        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STUDENT_NIM, student.getNim());
        contentValues.put(Config.COLUMN_STUDENT_NAME, student.getName());
        contentValues.put(Config.COLUMN_STUDENT_EMAIL, student.getEmail());
        contentValues.put(Config.COLUMN_STUDENT_PHONE, student.getPhone());
        contentValues.put(Config.COLUMN_REGISTRATION_NUMBER, idProdi);
        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_STUDENT, null, contentValues);
        } catch (SQLiteException e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return id;
    }

    public List<Student> getAllStudentByRegNo(long registrationNo) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Student> studentList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_STUDENT,
                    new String[] {Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NIM, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_PHONE, Config.COLUMN_STUDENT_EMAIL},
                    Config.COLUMN_REGISTRATION_NUMBER + " = ? ",
                    new String[] {String.valueOf(registrationNo)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                studentList = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID));
                    String nim = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NIM));
                    String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_EMAIL));

                    studentList.add(new Student(id,nim,name,phone,email));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return studentList;
    }

    public Student getStudentById(int idStudent) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        Student student = null;
        try {
            cursor = sqLiteDatabase.query(Config.TABLE_STUDENT, null,
                    Config.COLUMN_STUDENT_ID + " = ? ", new String[]{String.valueOf(idStudent)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID));
                String nim = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NIM));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_EMAIL));
                student = new Student(id, nim, name, phone, email);
            }
        } catch (Exception e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return student;
    }

    public long updateStudentInfo(Student student) {

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STUDENT_NIM, student.getNim());
        contentValues.put(Config.COLUMN_STUDENT_NAME, student.getName());
        contentValues.put(Config.COLUMN_STUDENT_PHONE, student.getPhone());
        contentValues.put(Config.COLUMN_STUDENT_EMAIL, student.getEmail());

        Log.e("ROWCOUNT","asasa " + contentValues);

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_STUDENT, contentValues,
                    Config.COLUMN_STUDENT_ID + " = ? ",
                    new String[]{String.valueOf(student.getId())});
            Log.e("ROWCOUNT","asasa " + rowCount);
        } catch (SQLiteException e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public boolean deleteStudentByid(long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_STUDENT,
                Config.COLUMN_STUDENT_ID + " = ? ", new String[]{String.valueOf(id)});

        return row > 0;
    }

    public boolean deleteAllStudentsByRegNum(long registrationNum) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_STUDENT,
                Config.COLUMN_REGISTRATION_NUMBER + " = ? ", new String[]{String.valueOf(registrationNum)});
        return row > 0;
    }

    public long getNumberOfStudent() {
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_STUDENT);
        } catch (SQLiteException e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return count;
    }


    // PRODI
    public long insertProdi(Prodi prodi) {

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PRODI_FULL_NAME, prodi.getFull_name());
        contentValues.put(Config.COLUMN_PRODI_NAME, prodi.getName());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_PRODI, null, contentValues);
        } catch (SQLiteException e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Prodi> getAllProdi() {

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(Config.TABLE_PRODI, null, null, null, null, null, null, null);
            if (cursor != null)
                if (cursor.moveToFirst()) {
                    List<Prodi> prodiList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODI_ID));
                        String full_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODI_FULL_NAME));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODI_NAME));

                        prodiList.add(new Prodi(id, full_name, name));
                    } while (cursor.moveToNext());

                    return prodiList;
                }
        } catch (Exception e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public Prodi getProdiById(long idProdi) {

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Prodi prodi = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_PRODI, null,
                    Config.COLUMN_PRODI_ID + " = ? ", new String[]{String.valueOf(idProdi)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODI_ID));
                String full_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODI_FULL_NAME));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODI_NAME));

                prodi = new Prodi(id, full_name, name);
            }
        } catch (Exception e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return prodi;
    }

    public long updateProdiInfo(Prodi prodi) {

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PRODI_FULL_NAME, prodi.getFull_name());
        contentValues.put(Config.COLUMN_PRODI_NAME, prodi.getName());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_PRODI, contentValues,
                    Config.COLUMN_PRODI_ID + " = ? ",
                    new String[]{String.valueOf(prodi.getId())});
            Log.e("ROWCOUNT","asasa " + rowCount);
        } catch (SQLiteException e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteProdiById(long idProdi) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_PRODI,
                    Config.COLUMN_PRODI_ID + " = ? ",
                    new String[]{String.valueOf(idProdi)});
        } catch (SQLiteException e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return deletedRowCount;
    }

    public boolean deleteAllProdi() {
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(Config.TABLE_PRODI, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_PRODI);

            if (count == 0)
                deleteStatus = true;

        } catch (SQLiteException e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }

    public long getNumberOfProdi() {
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_PRODI);
        } catch (SQLiteException e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return count;
    }


}
