package com.example.riddlemethis;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedRiddlesFragment extends Fragment {

    private List<Riddle> savedRiddles;

    private ListView savedRiddlesView;
    private ProgressBar progressBar;
    private View rootView;

    private ViewRiddleViewModel model;

    public SavedRiddlesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(ViewRiddleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_saved_riddles, container, false);
        progressBar = rootView.findViewById(R.id.progressBar_savedRiddles_progress);
        savedRiddlesView = rootView.findViewById(R.id.recyclerView_savedRiddles_listView);
        savedRiddlesView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        setListeners();

        return rootView;
    }

    private void setListeners() {
        savedRiddlesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putParcelable("riddle", savedRiddles.get(position));
                args.putBoolean("isDiscovered", false);
                Navigation.findNavController(parent).navigate(R.id.action_savedRiddlesFragment_to_viewRiddleFragment,
                        args);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadSavedRiddles();
    }

    private void loadSavedRiddles() {
        LoadRelationsQueryBuilder<Riddle> loadRelationsQueryBuilder;
        loadRelationsQueryBuilder = LoadRelationsQueryBuilder.of(Riddle.class);
        loadRelationsQueryBuilder.setRelationName("savedRiddles");

        Backendless.Data.of(BackendlessUser.class).loadRelations(Backendless.UserService.loggedInUser(),
                loadRelationsQueryBuilder, new AsyncCallback<List<Riddle>>() {
                    @Override
                    public void handleResponse(List<Riddle> response) {
                        savedRiddles = response;
                        savedRiddlesView.setAdapter(new SavedRiddlesAdapter(getContext(),
                                savedRiddles));
                        progressBar.setVisibility(View.INVISIBLE);
                        savedRiddlesView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
    }

}
