package com.example.multipletablesqlite.features.prodi_crud.show_prodi_list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipletablesqlite.R;
import com.example.multipletablesqlite.database.DatabaseQueryClass;
import com.example.multipletablesqlite.features.prodi_crud.update_prodi_info.ProdiUpdateDialogFragment;
import com.example.multipletablesqlite.features.prodi_crud.update_prodi_info.ProdiUpdateListener;
import com.example.multipletablesqlite.features.student_crud.show_student_list.StudentListActivity;
import com.example.multipletablesqlite.model.Prodi;
import com.example.multipletablesqlite.util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class ProdiListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Prodi> prodiList;
    private DatabaseQueryClass databaseQueryClass;

    public ProdiListRecyclerViewAdapter(Context context, List<Prodi> prodiList) {
        this.context = context;
        this.prodiList = prodiList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_prodi, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        final int listPosition = position;
        final Prodi prodi = prodiList.get(position);

        holder.fullNameTextView.setText(prodi.getFull_name());
        holder.nameTextView.setText(prodi.getName());

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this prodi?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                prodiDelete(listPosition);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
            public void onClick(View v) {
                ProdiUpdateDialogFragment prodiUpdateDialogFragment = ProdiUpdateDialogFragment.newInstance(prodi.getId(), listPosition, new ProdiUpdateListener() {
                    @Override
                    public void onProdiInfoUpdated(Prodi prodi, int position) {
                        prodiList.set(position, prodi);
                        notifyDataSetChanged();
                    }
                });
                prodiUpdateDialogFragment.show(((ProdiListActivity) context).getSupportFragmentManager(), Config.UPDATE_PRODI);
            }

        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StudentListActivity.class);
                String hai = String.valueOf(prodi.getId());
                Log.d("ANJING",hai);

                intent.putExtra(Config.PRODI_REGISTRATION, prodi.getId());
                context.startActivity(intent);
            }
        });

    }

//    private void editProdi(long prodiId, int listPosition){
//        ProdiUpdateDialogFragment prodiUpdateDialogFragment = ProdiUpdateDialogFragment.newInstance(prodiId, listPosition, new ProdiUpdateListener() {
//            @Override
//            public void onProdiInfoUpdated(Prodi prodi, int position) {
//                prodiList.set(position, prodi);
//                notifyDataSetChanged();
//            }
//        });
//        prodiUpdateDialogFragment.show(((ProdiListActivity) context).getSupportFragmentManager(), Config.UPDATE_PRODI);
//    }

    public void prodiDelete(int position) {

        Prodi prodi = prodiList.get(position);
        long count = databaseQueryClass.deleteProdiById(prodi.getId());

        if(count>0){
            prodiList.remove(position);
            notifyDataSetChanged();
            ((ProdiListActivity) context).viewVisibility();
            Toast.makeText(context, "Prodi deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Prodi not deleted. Something wrong!", Toast.LENGTH_LONG).show();
    }


    @Override
    public int getItemCount() {
        return prodiList.size();
    }
}
