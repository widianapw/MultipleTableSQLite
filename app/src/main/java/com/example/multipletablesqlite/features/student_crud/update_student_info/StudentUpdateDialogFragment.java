package com.example.multipletablesqlite.features.student_crud.update_student_info;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.multipletablesqlite.R;
import com.example.multipletablesqlite.database.DatabaseQueryClass;
import com.example.multipletablesqlite.model.Student;
import com.example.multipletablesqlite.util.Config;

public class StudentUpdateDialogFragment extends DialogFragment {
    private EditText studentNameEditText;
    private EditText studentNimEditText;
    private EditText studentPhoneEditText;
    private EditText studentEmailEditText;
    private Button updateButton;
    private Button cancelButton;
    private DatabaseQueryClass databaseQueryClass;
    private Student mStudent;

    private static StudentUpdateListener studentUpdateListener;
    private static long studentId;
    private static int position;

    public StudentUpdateDialogFragment() {

    }

    public static StudentUpdateDialogFragment newInstance(long studId, int pos, StudentUpdateListener listener){
        studentId = studId;
        position = pos;
        studentUpdateListener = listener;

        StudentUpdateDialogFragment studentUpdateDialogFragment = new StudentUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update student information");
        studentUpdateDialogFragment.setArguments(args);

        studentUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return studentUpdateDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_student_update_dialog_fragment, container, false);
        studentNimEditText = view.findViewById(R.id.studentNimEditText);
        studentNameEditText = view.findViewById(R.id.studentNameEditText);
        studentPhoneEditText = view.findViewById(R.id.studentPhoneEditText);
        studentEmailEditText = view.findViewById(R.id.studentEmailEditText);
        updateButton = view.findViewById(R.id.updateStudentInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        databaseQueryClass = new DatabaseQueryClass(getContext());
        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        Student student = databaseQueryClass.getStudentById((int) studentId);
        Log.e("STUDENTT","sdf "+student.getEmail());

        studentNimEditText.setText(student.getNim());
        studentNameEditText.setText(student.getName());
        studentPhoneEditText.setText(student.getPhone());
        studentEmailEditText.setText(student.getEmail());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentNim = studentNimEditText.getText().toString();
                String studentName = studentNameEditText.getText().toString();
                String studentPhone = studentPhoneEditText.getText().toString();
                String studentEmail = studentEmailEditText.getText().toString();

                Student student = new Student(-1, studentNim, studentName, studentPhone, studentEmail);

                long rowCount = databaseQueryClass.updateStudentInfo(student);

                if (rowCount > 0) {
                    studentUpdateListener.onStudentInfoUpdate(student, position);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }
}
