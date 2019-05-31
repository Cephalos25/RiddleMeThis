package com.example.riddlemethis;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyRiddlesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView myRiddlesListView;
    
    private SharedViewModel model;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myriddles, container, false);
        wireWidgets(rootView);
        setListeners();
        populateListView();
        return rootView;
    }

    public MyRiddlesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.setCurrentFragment(MyRiddlesFragment.class);
    }
        

    private void populateListView() {
    }

    private void setListeners() {
        myRiddlesListView.setOnItemClickListener(this);
    }

    private void wireWidgets(View rootView) {
        myRiddlesListView = rootView.findViewById(R.id.listview_myriddles_savedriddles);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
