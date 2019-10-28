package com.example.multipletablesqlite.util;

public class Config {
    public static final String DATABASE_NAME = "students";

    //column names of student table
    public static final String TABLE_STUDENT = "student";
    public static final String COLUMN_STUDENT_ID = "_id";
    public static final String COLUMN_REGISTRATION_NUMBER = "fk_registration_no";
    public static final String COLUMN_STUDENT_NIM = "nim";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_PHONE = "phone";
    public static final String COLUMN_STUDENT_EMAIL = "email";
    public static final String PRODI_SUB_CONSTRAINT = "prodi_sub_unique";

    //column names of prodi table
    public static final String TABLE_PRODI = "prodi";
    public static final String COLUMN_PRODI_ID = "_id";
    public static final String COLUMN_PRODI_FULL_NAME = "full_name";
    public static final String COLUMN_PRODI_NAME = "name";

    public static final String TITLE = "title";
    public static final String CREATE_STUDENT = "create_student";
    public static final String UPDATE_STUDENT = "update_student";
    public static final String CREATE_PRODI = "create_prodi";
    public static final String UPDATE_PRODI = "update_prodi";
    public static final String PRODI_REGISTRATION = "prodi_registration";



}
