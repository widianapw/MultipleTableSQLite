package com.example.multipletablesqlite.features.student_crud.create_student;

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

public class StudentCreateDialogFragment extends DialogFragment {
    private static int idProdi;
    private static StudentCreateListener studentCreateListener;

    private EditText studentNameEditText;
    private EditText studentNimEditText;
    private EditText studentPhoneEditText;
    private EditText studentEmailEditText;
    private Button createButton;
    private Button cancelButton;

    private String studentNim = "";
    private String studentName = "";
    private String studentPhone = "";
    private String studentEmail = "";

    public StudentCreateDialogFragment() {

    }

    public static StudentCreateDialogFragment newInstance(int id_Prodi, StudentCreateListener listener){
        idProdi = id_Prodi;
        String testt = String.valueOf(idProdi);
        Log.d("LOLOK!",testt);
        studentCreateListener = listener;

        StudentCreateDialogFragment studentCreateDialogFragment = new StudentCreateDialogFragment();

        studentCreateDialogFragment.setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return studentCreateDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_student_create_dialog_fragment, container, false);
        getDialog().setTitle("Add New Student");

        studentNimEditText =view.findViewById(R.id.studentNimEditText);
        studentNameEditText =view.findViewById(R.id.studentNameEditText);
        studentPhoneEditText =view.findViewById(R.id.studentPhoneEditText);
        studentEmailEditText =view.findViewById(R.id.studentEmailEditText);

        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                studentNim = studentNimEditText.getText().toString();
                studentName = studentNameEditText.getText().toString();
                studentPhone = studentPhoneEditText.getText().toString();
                studentEmail = studentEmailEditText.getText().toString();

                


                Student student = new Student(-1,studentNim,studentName,studentPhone,studentEmail);
                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());
                long id = databaseQueryClass.insertStudent(student, idProdi);

                if(id>0){
                    student.setId(id);
                    studentCreateListener.onStudentCreated(student);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
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
