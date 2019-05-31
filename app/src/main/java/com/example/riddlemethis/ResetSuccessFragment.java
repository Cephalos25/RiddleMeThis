package com.example.riddlemethis;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


@LoginSection
public class ResetSuccessFragment extends Fragment {

    private Button buttonBack;
    private View rootView;

    public ResetSuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_reset_success, container, false);

        buttonBack = rootView.findViewById(R.id.button_resetsuccess_back);
        setListeners();

        return rootView;
    }

    private void setListeners() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_resetSuccessFragment_to_loginFragment);
            }
        });
    }

}
