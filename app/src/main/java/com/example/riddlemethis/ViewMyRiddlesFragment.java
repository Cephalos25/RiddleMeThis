package com.example.riddlemethis;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class ViewMyRiddlesFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
private TextView textViewRiddleTitle;
private TextView textViewRiddleText;
private Button buttonDelete;
private Button buttonEdit;
private TextView textViewRiddleAnswer;

private EditText editTextRiddle;
private EditText editTextAnswer;
private Button buttonSave;

private Switch switchRevealAnswer;

    public ViewMyRiddlesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentwireWidgets();
        View rootView = inflater.inflate(R.layout.fragment_view_my_riddles, container, false);
        wireWidgets(rootView);
        setListeners();
        populateFragment();
        return rootView;
    }

    private void populateFragment() {
        //TODO receive riddle from selected choice in MyRiddlesFragment or
        // DiscoverRiddlesFragment and populate the textviews

    }

    private void setListeners() {
        switchRevealAnswer.setOnCheckedChangeListener(this);
        buttonDelete.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);
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
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_viewmyriddles_edit:
                //TODO create edit riddle capability
                editRiddle(v);
            case R.id.button_viewmyriddles_delete:
                //TODO create delete riddle capability
                deleteRiddle();


        }

    }



    private void deleteRiddle() {
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
        textViewRiddleAnswer.setTextColor(0xfff);
    }

    private void revealAnswer() {
        textViewRiddleAnswer.setTextColor(0000);
    }
}
