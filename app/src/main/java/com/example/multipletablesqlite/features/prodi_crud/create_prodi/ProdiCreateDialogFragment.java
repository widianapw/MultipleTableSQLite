package com.example.multipletablesqlite.features.prodi_crud.create_prodi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.multipletablesqlite.R;
import com.example.multipletablesqlite.database.DatabaseQueryClass;
import com.example.multipletablesqlite.model.Prodi;
import com.example.multipletablesqlite.util.Config;

public class ProdiCreateDialogFragment extends DialogFragment {
    private static ProdiCreateListener prodiCreateListener;

    private EditText fullnameEditText;
    private EditText nameEditText;
    private Button createButton;
    private Button cancelButton;

    private String nameString = "";
    private String fullNameString = "";

    public ProdiCreateDialogFragment(){

    }

    public static ProdiCreateDialogFragment newinstance( String title, ProdiCreateListener listener){
        prodiCreateListener = listener;
        ProdiCreateDialogFragment prodiCreateDialogFragment = new ProdiCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        prodiCreateDialogFragment.setArguments(args);

        prodiCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return prodiCreateDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_prodi_create_dialog_fragment, container, false);

        fullnameEditText = view.findViewById(R.id.prodiFullNameEditText);
        nameEditText = view.findViewById(R.id.prodiNameEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullNameString = fullnameEditText.getText().toString();
                nameString = nameEditText.getText().toString();


                Prodi prodi = new Prodi(-1,fullNameString, nameString);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                long id = databaseQueryClass.insertProdi(prodi);

                if(id>0){
                    prodi.setId(id);
                    prodiCreateListener.onProdiCreated(prodi);
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
