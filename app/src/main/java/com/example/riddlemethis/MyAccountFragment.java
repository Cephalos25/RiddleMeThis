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
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


public class MyAccountFragment extends Fragment {


    private SharedViewModel model;

    private View rootView;
    private TextView textViewUsername;
    private TextView textViewEmail;
    private Button buttonEditAccount;
    private Button buttonLogout;
    private TextView textViewError;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.setCurrentFragment(MyAccountFragment.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_account, container, false);

        textViewUsername = rootView.findViewById(R.id.textView_myAccount_name);
        textViewEmail = rootView.findViewById(R.id.textView_myAccount_email);
        buttonEditAccount = rootView.findViewById(R.id.button_myAccount_editAccount);
        buttonLogout = rootView.findViewById(R.id.button_myAccount_logout);
        textViewError = rootView.findViewById(R.id.textView_myAccount_error);

        textViewUsername.setText((String) Backendless.UserService.CurrentUser().getProperty("username"));
        textViewEmail.setText(Backendless.UserService.CurrentUser().getEmail());

        setListeners();

        return rootView;
    }

    private void setListeners() {
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Navigation.findNavController(v).navigate(R.id.action_global_loginGraph);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        textViewError.setText(String.format(getString(R.string.all_fault), fault.getCode(),
                                fault.getMessage()));
                    }
                });
            }
        });
    }

}
