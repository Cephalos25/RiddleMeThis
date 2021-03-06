package com.example.riddlemethis;


import android.os.Bundle;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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


@LoginSection
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private EditText emailField;
    private EditText passwordField;
    private Button buttonLogin;
    private CheckBox checkStayLogged;
    private TextView buttonForgotPassword;
    private TextView buttonCreateAccount;
    private View rootView;

    private SharedViewModel model;
    private OnLoginFragmentInteractionListener activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.setCurrentFragment(LoginFragment.class);
        if(getActivity() instanceof OnLoginFragmentInteractionListener) {
            activity = (OnLoginFragmentInteractionListener) getActivity();
        } else {
            throw new IllegalStateException("Activity must implement " +
                    "OnLoginFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        wireWidgets();
        setListeners();
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
            public void onClick(final View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                boolean stayLoggedIn = checkStayLogged.isChecked();
                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Log.d(TAG, "handleResponse: " + response.getEmail() + " successfully logged in.");
                        activity.onLoginAttempt();
                        Navigation.findNavController(v).navigate(R.id.action_loginGraph_myRiddlesGraph);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e(TAG, "handleFault: " + fault.getMessage());
                    }
                }, stayLoggedIn);
            }
        });
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_createAccountFragment);
            }
        });
        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_resetPasswordFragment);
            }
        });
    }
  
    public interface OnLoginFragmentInteractionListener {
        public void onLoginAttempt();
    }
}
