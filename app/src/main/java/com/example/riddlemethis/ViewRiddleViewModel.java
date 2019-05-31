package com.example.riddlemethis;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.backendless.BackendlessUser;

public class ViewRiddleViewModel extends ViewModel {
    private Fragment returnFragment;

    public Fragment getReturnFragment() {
        return returnFragment;
    }

    public void setReturnFragment(Fragment returnFragment) {
        this.returnFragment = returnFragment;
    }
}
