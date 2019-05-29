package com.example.riddlemethis;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private RecyclerView savedRiddlesView;
    private ProgressBar progressBar;
    private View rootView;

    public SavedRiddlesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSavedRiddles();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_saved_riddles, container, false);
        progressBar = rootView.findViewById(R.id.progressBar_savedRiddles_progress);
        savedRiddlesView = rootView.findViewById(R.id.recyclerView_savedRiddles_listView);
        savedRiddlesView.setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
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
                        progressBar.setVisibility(View.INVISIBLE);
                        savedRiddlesView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
    }

}
