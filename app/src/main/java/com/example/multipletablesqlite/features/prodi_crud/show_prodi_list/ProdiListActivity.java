package com.example.multipletablesqlite.features.prodi_crud.show_prodi_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.multipletablesqlite.R;
import com.example.multipletablesqlite.database.DatabaseQueryClass;
import com.example.multipletablesqlite.features.prodi_crud.create_prodi.ProdiCreateDialogFragment;
import com.example.multipletablesqlite.features.prodi_crud.create_prodi.ProdiCreateListener;
import com.example.multipletablesqlite.model.Prodi;
import com.example.multipletablesqlite.util.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProdiListActivity extends AppCompatActivity implements ProdiCreateListener {

    private long idProdi;

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Prodi> prodiList = new ArrayList<>();

    private TextView summaryTextView;
    private TextView prodiListEmptyTextView;
    private RecyclerView recyclerView;
    private ProdiListRecyclerViewAdapter prodiListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodi_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = findViewById(R.id.recyclerView);
//        summaryTextView = findViewById(R.id.summaryTextView);
        prodiListEmptyTextView = findViewById(R.id.emptyListTextView);

        prodiList.addAll(databaseQueryClass.getAllProdi());

        prodiListRecyclerViewAdapter = new ProdiListRecyclerViewAdapter(this, prodiList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(prodiListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProdiCreateDialog();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        printSummary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, You wanted to delete all prodis?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = databaseQueryClass.deleteAllProdi();
                            if(isAllDeleted){
                                prodiList.clear();
                                prodiListRecyclerViewAdapter.notifyDataSetChanged();
                                viewVisibility();
                            }
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

        return super.onOptionsItemSelected(item);
    }

    private void openProdiCreateDialog() {
        ProdiCreateDialogFragment prodiCreateDialogFragment = ProdiCreateDialogFragment.newinstance("Create Prodi", this);
        prodiCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_PRODI);
    }

//    private void printSummary() {
//        long prodiNum = databaseQueryClass.getNumberOfProdi();
//        long studentNum = databaseQueryClass.getNumberOfStudent();
//
//        summaryTextView.setText(getResources().getString(R.string.database_summary, studentNum, studentNum));
//    }

    public void viewVisibility() {
        if(prodiList.isEmpty())
            prodiListEmptyTextView.setVisibility(View.VISIBLE);
        else
            prodiListEmptyTextView.setVisibility(View.GONE);
//        printSummary();
    }

    @Override
    public void onProdiCreated(Prodi prodi) {
        prodiList.add(prodi);
        prodiListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(prodi.getFull_name());
    }
}
