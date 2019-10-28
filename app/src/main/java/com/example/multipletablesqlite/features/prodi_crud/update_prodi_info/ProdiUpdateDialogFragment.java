package com.example.multipletablesqlite.features.prodi_crud.update_prodi_info;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


public class ProdiUpdateDialogFragment extends DialogFragment {

    private static ProdiUpdateListener prodiUpdateListener;
    private static int prodiItemPosition;
    private static long prodiId;
    private Prodi mProdi;
    private EditText fullnameEditText;
    private EditText nameEditText;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "";
    private String fullNameString = "";
    private DatabaseQueryClass databaseQueryClass;

    public ProdiUpdateDialogFragment() {

    }

    public static ProdiUpdateDialogFragment newInstance(long id, int position, ProdiUpdateListener listener) {
        prodiId = id;
        prodiItemPosition = position;
        prodiUpdateListener = listener;
        ProdiUpdateDialogFragment prodiUpdateDialogFragment = new ProdiUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "UPDATED");
        prodiUpdateDialogFragment.setArguments(args);
        prodiUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return prodiUpdateDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_prodi_update_dialog_fragment, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());
        fullnameEditText = view.findViewById(R.id.prodiFullNameEditText);
        nameEditText = view.findViewById(R.id.prodiNameEditText);
        updateButton = view.findViewById(R.id.updateProdiInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mProdi =databaseQueryClass.getProdiById(prodiId);

        if(mProdi!=null){
            fullnameEditText.setText(mProdi.getFull_name());
            nameEditText.setText(mProdi.getName());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fullNameString = fullnameEditText.getText().toString();
                    nameString = nameEditText.getText().toString();

                    mProdi.setFull_name(fullNameString);
                    mProdi.setName(nameString);


                    long id = databaseQueryClass.updateProdiInfo(mProdi);

                    if(id>0){
                        prodiUpdateListener.onProdiInfoUpdated(mProdi, prodiItemPosition);
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

        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
