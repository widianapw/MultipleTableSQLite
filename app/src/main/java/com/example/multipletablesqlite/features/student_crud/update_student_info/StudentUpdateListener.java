package com.example.multipletablesqlite.features.student_crud.update_student_info;

import com.example.multipletablesqlite.model.Student;

public interface StudentUpdateListener {
    void onStudentInfoUpdate(Student student, int position);
}
