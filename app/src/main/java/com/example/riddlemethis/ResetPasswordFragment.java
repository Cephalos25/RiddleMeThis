package com.example.riddlemethis;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {
    public static final String TAG = "ResetPasswordFragment";

    private View rootView;
    private EditText inputEmail;
    private Button buttonReset;
    private TextView buttonRememberPassword;
    private TextView textViewError;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_reset_password, container, false);

        inputEmail = rootView.findViewById(R.id.editText_resetpassword_email);
        buttonReset = rootView.findViewById(R.id.button_resetpassword_reset);
        buttonRememberPassword = rootView.findViewById(R.id.textView_resetpassword_remember);
        textViewError = rootView.findViewById(R.id.textView_resetpassword_error);

        setListeners();

        return rootView;
    }

    private void setListeners() {
        buttonRememberPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Backendless.UserService.restorePassword(inputEmail.getText().toString(),
                        new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Navigation.findNavController(v).navigate(R.id.action_resetPasswordFragment_to_resetSuccessFragment);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        textViewError.setText(String.format(getString(R.string.all_fault),
                                fault.getCode(), fault.getMessage()));
                        Log.e(MainActivity.BACKENDLESS_TAG, fault.getDetail());
                    }
                });
            }
        });
    }

}
