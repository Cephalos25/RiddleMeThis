package com.example.riddlemethis;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


public class CreateRiddleFragment extends Fragment implements View.OnClickListener {

    private Button buttonSaveRiddle;
    private EditText editTextName;
    private EditText editTextRiddle;
    private EditText editTextAnswer;
    private EditText editTextHint;
    private Button buttonBack;

    private SharedViewModel model;

    public CreateRiddleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.setCurrentFragment(CreateRiddleFragment.class);
    }
  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_riddle, container, false);
        wireWidgets(v);
        setListeners();
        return v;
    }

    private void setListeners() {
        buttonSaveRiddle.setOnClickListener(this);
    }

    private void wireWidgets(View view) {
        buttonSaveRiddle = view.findViewById(R.id.button_createriddlefragment_saveriddle);
        editTextName = view.findViewById(R.id.editText_createriddle_name);
        editTextAnswer = view.findViewById(R.id.edittext_createriddlefragment_answer);
        editTextRiddle = view.findViewById(R.id.edittext_createriddlefragment_riddle);
        editTextHint = view.findViewById(R.id.edittext_createriddlefragment_hint);
        buttonBack = view.findViewById(R.id.button_createriddle_back);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_createriddlefragment_saveriddle:
                saveRiddle(v);
            case R.id.button_createriddle_back:
                Navigation.findNavController(v).navigate(R.id.action_createRiddleFragment_to_myRiddlesFragment);
        }
    }

    private void saveRiddle(final View view) {
        Riddle tempRiddle = new Riddle();
        tempRiddle.setName(editTextName.getText().toString());
        tempRiddle.setText(editTextRiddle.getText().toString());
        tempRiddle.setCorrectAnswer(editTextAnswer.getText().toString());
        Backendless.Data.of(Riddle.class).save(tempRiddle, new AsyncCallback<Riddle>() {
            @Override
            public void handleResponse(Riddle response) {
                Navigation.findNavController(view).navigate(R.id.action_createRiddleFragment_to_myRiddlesFragment);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getContext(), fault.getCode() + " - " + fault.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
