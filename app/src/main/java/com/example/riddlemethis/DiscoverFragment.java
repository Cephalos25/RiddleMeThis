package com.example.riddlemethis;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.LoadRelationsQueryBuilder;

import java.util.Collections;
import java.util.List;


public class DiscoverFragment extends Fragment {

    private View rootView;
    private ListView listView;
    private ProgressBar progressBar;

    private SharedViewModel model;
    private List<Riddle> riddles;
    private List<Riddle> savedRiddles;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.setCurrentFragment(DiscoverFragment.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        wireWidgets(rootView);
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        setListeners();
        return rootView;
    }

    private void setListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Riddle riddle = riddles.get(position);
                Bundle args = new Bundle();
                args.putParcelable("riddle", riddle);
                args.putBoolean("isDiscovered", true);
                Navigation.findNavController(view).navigate(R.id.action_discoverFragment_to_viewRiddleFragment,
                        args);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Backendless.Data.of(Riddle.class).find(new AsyncCallback<List<Riddle>>() {
            @Override
            public void handleResponse(List<Riddle> response) {
                Collections.shuffle(response);
                riddles = response;
                populateListView();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void wireWidgets(View rootView) {
        listView = rootView.findViewById(R.id.listview_discover_list);
        progressBar = rootView.findViewById(R.id.progressBar_discover_loadingBar);
    }

    private void populateListView(){
        if(Backendless.UserService.CurrentUser() != null) {
            LoadRelationsQueryBuilder<Riddle> loadRelationsQueryBuilder;
            loadRelationsQueryBuilder = LoadRelationsQueryBuilder.of(Riddle.class);
            loadRelationsQueryBuilder.setRelationName("savedRiddles");

            Backendless.Data.of(BackendlessUser.class).loadRelations(Backendless.UserService.loggedInUser(),
                    loadRelationsQueryBuilder, new AsyncCallback<List<Riddle>>() {
                        @Override
                        public void handleResponse(List<Riddle> response) {
                            savedRiddles = response;
                            listView.setAdapter(new DiscoveredRiddlesAdapter(getContext(),
                                    R.layout.listitem_discoverriddles_riddle, riddles, savedRiddles));
                            progressBar.setVisibility(View.INVISIBLE);
                            listView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
        } else {
            listView.setAdapter(new SavedRiddlesAdapter(getContext(), riddles));
            progressBar.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }
}
