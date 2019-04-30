package com.example.riddlemethis;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText emailField;
    private EditText passwordField;
    private Button buttonLogin;
    private CheckBox checkStayLogged;
    private TextView buttonForgotPassword;
    private TextView buttonCreateAccount;
    private View rootView;

    private SharedViewModel model;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        return rootView;
    }

    private void wireWidgets() {
        emailField = rootView.findViewById(R.id.editText_login_email);
        passwordField = rootView.findViewById(R.id.editText_login_password);
        buttonLogin = rootView.findViewById(R.id.button_login_login);
        checkStayLogged = rootView.findViewById(R.id.checkBox_login_stayLogged);
        buttonForgotPassword = rootView.findViewById(R.id.textView_login_forgotPassword);
        buttonCreateAccount = rootView.findViewById(R.id.textView_login_noAccount);
    }

    private void setListeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                boolean stayLoggedIn = checkStayLogged.isChecked();
                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        model.setLoggedInUser(response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                }, stayLoggedIn);
            }
        });
    }

}
