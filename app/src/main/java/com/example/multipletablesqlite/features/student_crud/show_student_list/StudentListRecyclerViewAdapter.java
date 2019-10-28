package com.example.multipletablesqlite.features.student_crud.show_student_list;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipletablesqlite.R;
import com.example.multipletablesqlite.database.DatabaseQueryClass;
import com.example.multipletablesqlite.features.prodi_crud.show_prodi_list.ProdiListActivity;
import com.example.multipletablesqlite.features.student_crud.update_student_info.StudentUpdateDialogFragment;
import com.example.multipletablesqlite.features.student_crud.update_student_info.StudentUpdateListener;
import com.example.multipletablesqlite.model.Student;
import com.example.multipletablesqlite.util.Config;

import java.util.List;

public class StudentListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Student> studentList;

    public StudentListRecyclerViewAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int listPosition = position;
        final Student student = studentList.get(position);

        holder.studentNimTextView.setText(student.getNim());
        holder.studentNameTextView.setText(student.getName());
        holder.studentPhoneTextView.setText(student.getPhone());
        holder.studentEmailTextView.setText(student.getEmail());
        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this student?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteStudent(student);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editStudent(student.getId(), listPosition);
            }
        });
    }

    private void editStudent(long studentId, int listPosition){
        StudentUpdateDialogFragment studentUpdateDialogFragment = StudentUpdateDialogFragment.newInstance(studentId, listPosition, new StudentUpdateListener() {
            @Override
            public void onStudentInfoUpdate(Student student, int position) {
                studentList.set(position, student);
                notifyDataSetChanged();
            }
        });
        studentUpdateDialogFragment.show(((StudentListActivity) context).getSupportFragmentManager(), Config.UPDATE_STUDENT);
    }

    private void deleteStudent(Student student) {
        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(context);
        boolean isDeleted = databaseQueryClass.deleteStudentByid(student.getId());

        if(isDeleted) {
            studentList.remove(student);
            notifyDataSetChanged();
            ((StudentListActivity) context).viewVisibility();
        } else
            Toast.makeText(context, "Cannot delete!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

}
