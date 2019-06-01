package com.example.riddlemethis;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;


public class ViewMyRiddleFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView textViewRiddleTitle;
    private TextView textViewRiddleText;
    private Button buttonDelete;
    private Button buttonEdit;
    private TextView textViewRiddleAnswer;
    private Button buttonBack;

    private EditText editTextRiddle;
    private EditText editTextAnswer;
    private Button buttonSave;
    private Button buttonRevealHint;
    private Switch switchRevealAnswer;
  
    private SharedViewModel model;
    private Riddle riddle;
  
    public ViewMyRiddleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.setCurrentFragment(ViewMyRiddleFragment.class);
        riddle = getArguments().getParcelable("riddle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_my_riddle, container, false);
        wireWidgets(rootView);
        setListeners();
        populateFragment();
        return rootView;
    }

    private void populateFragment() {
        textViewRiddleTitle.setText(riddle.getName().concat(":"));
        textViewRiddleText.setText(riddle.getText());
        textViewRiddleAnswer.setText(riddle.getCorrectAnswer());
        editTextRiddle.setText(riddle.getText());
        editTextAnswer.setText(riddle.getCorrectAnswer());
    }

    private void setListeners() {
        switchRevealAnswer.setOnCheckedChangeListener(this);
        buttonDelete.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);
        buttonRevealHint.setOnClickListener(this);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_viewMyRiddleFragment_to_myRiddlesFragment);
            }
        });
    }

    private void wireWidgets(View view) {
        textViewRiddleAnswer = view.findViewById(R.id.textview_viewfragment_answer);
        textViewRiddleText = view.findViewById(R.id.textview_viewfragment_riddle);
        textViewRiddleTitle = view.findViewById(R.id.textview_viewfragment_riddletitle);

        switchRevealAnswer = view.findViewById(R.id.switch_viewfragment_reveal);

        buttonEdit = view.findViewById(R.id.button_viewmyriddles_edit);
        buttonDelete = view.findViewById(R.id.button_viewmyriddles_delete);

        editTextAnswer = view.findViewById(R.id.edittext_myriddlepopup_answer);
        editTextRiddle = view.findViewById(R.id.edittext_myriddlepopup_riddle);

        buttonSave = view.findViewById(R.id.button_myriddlepopup_save);
        buttonRevealHint = view.findViewById(R.id.button_viewmyriddlefragment_viewhint);

        buttonBack = view.findViewById(R.id.button_viewmyriddle_back);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_viewmyriddles_edit:
                editRiddle(v);
            case R.id.button_viewmyriddles_delete:
                //TODO create delete riddle capability
                deleteRiddle();
            case R.id.button_viewmyriddlefragment_viewhint:
                //TODO create the view hint capability
                viewHint();
            case R.id.button_myriddlepopup_save:
                saveRiddle();

        }

    }

    private void saveRiddle() {
        //Add saving code here
        Navigation.findNavController(buttonSave).navigate(R.id.action_viewMyRiddleFragment_to_myRiddlesFragment);
    }

    private void viewHint() {

    }


    private void deleteRiddle() {
        //Add deleting code here
        Navigation.findNavController(buttonDelete).navigate(R.id.action_viewMyRiddleFragment_to_myRiddlesFragment);
    }

    private void editRiddle(View v) {

        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.edit_my_riddle_popup, null);

        int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0 , 0);


        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(buttonDelete.isPressed()){
                    textViewRiddleAnswer.setText(editTextAnswer.getText());
                    textViewRiddleText.setText(editTextRiddle.getText());
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            revealAnswer();
        }
        else{
            hideAnswer();
        }
    }

    private void hideAnswer() {
        textViewRiddleAnswer.setVisibility(View.INVISIBLE);
    }

    private void revealAnswer() {
        textViewRiddleAnswer.setVisibility(View.VISIBLE);
    }
}
