package com.example.riddlemethis;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private Class<? extends Fragment> currentFragment;

    public Class<? extends Fragment> getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Class<? extends Fragment> currentFragment) {
        this.currentFragment = currentFragment;
    }
}
