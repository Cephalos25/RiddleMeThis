package com.example.riddlemethis;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private Fragment currentFragment;

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }
}
