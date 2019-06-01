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


@LoginSection
public class ResetSuccessFragment extends Fragment {

    private Button buttonBack;
    private View rootView;

    private SharedViewModel model;

    public ResetSuccessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.setCurrentFragment(ResetSuccessFragment.class);
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
