package com.example.riddlemethis;

import androidx.lifecycle.ViewModel;

import com.backendless.BackendlessUser;

public class SharedViewModel extends ViewModel {
    private BackendlessUser loggedInUser;


    public BackendlessUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(BackendlessUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
