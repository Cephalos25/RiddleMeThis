package com.example.riddlemethis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class CreateRiddleFragment extends Fragment implements View.OnClickListener {




    private Button buttonSaveRiddle;
    private EditText editTextRiddle;
    private EditText editTextAnswer;
    private EditText editTextHint;




    public CreateRiddleFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_create_riddle, container, false);
        wireWidgets(v);
        setListeners();
        return v;
    }

    private void setListeners() {
        buttonSaveRiddle.setOnClickListener(this);
    }

    private void wireWidgets(View view) {
        buttonSaveRiddle = view.findViewById(R.id.button_createriddlefragment_saveriddle);
        editTextAnswer = view.findViewById(R.id.edittext_createriddlefragment_answer);
        editTextRiddle = view.findViewById(R.id.edittext_createriddlefragment_riddle);
        editTextHint = view.findViewById(R.id.edittext_createriddlefragment_hint);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_createriddlefragment_saveriddle:
                saveRiddle();
        }
    }

    private void saveRiddle() {
    }




}
