package com.example.riddlemethis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


public class ViewRiddlesFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
private TextView textViewRiddleTitle;
private TextView textViewRiddleText;
private TextView textViewRiddleAnswer;

private Switch switchRevealAnswer;

    public ViewRiddlesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentwireWidgets();
        View rootView = inflater.inflate(R.layout.fragment_view_riddles, container, false);
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
    }

    private void wireWidgets(View view) {
        textViewRiddleAnswer = view.findViewById(R.id.textview_viewfragment_answer);
        textViewRiddleText = view.findViewById(R.id.textview_viewfragment_riddle);
        textViewRiddleTitle = view.findViewById(R.id.textview_viewfragment_riddletitle);

        switchRevealAnswer = view.findViewById(R.id.switch_viewfragment_reveal);
    }


    @Override
    public void onClick(View v) {

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
