package com.example.riddlemethis;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class MyRiddlesFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private List<Riddle> myRiddlesList = new ArrayList<>();
    private ListView myRiddlesListView;
    private ArrayAdapter adapter;
    private Button buttonCreateNewRiddle;
    private ProgressBar progressBar;
  
    private SharedViewModel model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myriddles, container, false);
        wireWidgets(rootView);
        setListeners();
        populateListView();
        myRiddlesListView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
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
                adapter.clear();
                adapter.addAll(response);
                progressBar.setVisibility(View.INVISIBLE);
                myRiddlesListView.setVisibility(View.VISIBLE);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(getClass().getSimpleName(), "handleFault: " + fault.getMessage());
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
        adapter = new ArrayAdapter<Riddle>(getContext(), android.R.layout.simple_list_item_1, myRiddlesList);
        myRiddlesListView.setAdapter(adapter);
    }

    private void setListeners() {
        myRiddlesListView.setOnItemClickListener(this);
        buttonCreateNewRiddle.setOnClickListener(this);
    }

    private void wireWidgets(View rootView) {
        myRiddlesListView = rootView.findViewById(R.id.listview_myriddles_list);
        buttonCreateNewRiddle = rootView.findViewById(R.id.button_myriddles_create);
        progressBar = rootView.findViewById(R.id.progressBar_myriddles_loading);
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
