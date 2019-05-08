package com.example.riddlemethis;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment {

    private EditText usernameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private Button buttonCreateAccount;
    private TextView buttonUseLogin;
    private TextView textViewError;
    private View rootView;

    public CreateAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_create_account, container, false);

        usernameField = rootView.findViewById(R.id.editText_createAccount_username);
        emailField = rootView.findViewById(R.id.editText_createAccount_email);
        passwordField = rootView.findViewById(R.id.editText_createAccount_password);
        confirmPasswordField = rootView.findViewById(R.id.editText_createAccount_confirmPassword);
        buttonCreateAccount = rootView.findViewById(R.id.button_createAccount_create);
        buttonUseLogin = rootView.findViewById(R.id.textView_createAccount_haveAccount);
        textViewError = rootView.findViewById(R.id.textView_createAccount_errorField);
        textViewError.setText("");

        setListeners();

        return rootView;
    }

    private void setListeners() {
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String username = usernameField.getText().toString();
                final String email = emailField.getText().toString();
                if(username.equals("") || email.equals("") || passwordField.getText().toString().equals("")
                || confirmPasswordField.getText().toString().equals("")){
                    textViewError.setText("All fields must be non-empty");
                } else if (!passwordField.getText().toString().equals(confirmPasswordField.getText().toString())) {
                    textViewError.setText("The confirm password field's contents must match those of the password field");
                    confirmPasswordField.setText("");
                } else {
                    BackendlessUser user = new BackendlessUser();
                    user.setProperty("username", username);
                    user.setEmail(email);
                    user.setPassword(passwordField.getText().toString());
                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            textViewError.setText("");
                            Bundle args = new Bundle();
                            args.putString("email", email);
                            Navigation.findNavController(v).navigate(R.id.action_createAccountFragment_to_loginFragment,
                                    args);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            textViewError.setText(String.format(getString(R.string.all_fault), fault.getCode(),
                                    fault.getMessage()));
                        }
                    });
                }
            }
        });
        buttonUseLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_createAccountFragment_to_loginFragment);
            }
        });
    }

}
