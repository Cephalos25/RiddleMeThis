package com.example.riddlemethis;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.servercode.annotation.BackendlessGrantAccess;

import java.util.List;
import java.util.prefs.BackingStoreException;

public class MyRiddlesFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private List<Riddle> myRiddlesList;
    private ListView myRiddlesListView;
    private Button buttonCreateNewRiddle;
  
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

    @Override
    public void onStart() {
        super.onStart();
        String whereClause = "ownerId = " + Backendless.UserService.loggedInUser();
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        Backendless.Data.of(Riddle.class).find(queryBuilder, new AsyncCallback<List<Riddle>>() {
            @Override
            public void handleResponse(List<Riddle> response) {
                myRiddlesList = response;
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
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
        buttonCreateNewRiddle.setOnClickListener(this);
    }

    private void wireWidgets(View rootView) {
        myRiddlesListView = rootView.findViewById(R.id.listview_myriddles_savedriddles);
        buttonCreateNewRiddle = rootView.findViewById(R.id.button_myriddles_create);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("riddle", myRiddlesList.get(position));
        Navigation.findNavController(parent).navigate(R.id.action_myRiddlesFragment_to_viewMyRiddleFragment,
                bundle);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_myriddles_create:
                Navigation.findNavController(v).navigate(R.id.action_myRiddlesFragment_to_createRiddleFragment);
        }
    }



}
