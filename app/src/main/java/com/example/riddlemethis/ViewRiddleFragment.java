package com.example.riddlemethis;


import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.LoadRelationsQueryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ViewRiddleFragment extends Fragment {

    private View rootView;
    private TextView nameField;
    private TextView riddleField;
    private TextView answerField;
    private CheckBox checkBoxIsSaved;
    private Switch showAnswer;
    private ProgressBar loading;
    private Button backButton;

    private SharedViewModel model;
    private Riddle riddle;
    private boolean isDiscovered;
    private boolean isSaved;

    public ViewRiddleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.setCurrentFragment(ViewRiddleFragment.class);
        isDiscovered = getArguments().getBoolean("isDiscovered");
        riddle = getArguments().getParcelable("riddle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_riddle, container, false);
        wireWidgets();
        showAnswer.setChecked(false);
        setLoadingVisibility();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadRiddle();
    }

    private void loadRiddle() {
        LoadRelationsQueryBuilder<Riddle> loadRelationsQueryBuilder;
        loadRelationsQueryBuilder = LoadRelationsQueryBuilder.of(Riddle.class);
        loadRelationsQueryBuilder.setRelationName("savedRiddles");

        Backendless.Data.of(BackendlessUser.class).loadRelations(Backendless.UserService.loggedInUser(),
                loadRelationsQueryBuilder, new AsyncCallback<List<Riddle>>() {
                    @Override
                    public void handleResponse(List<Riddle> response) {
                        isSaved = response.contains(riddle);
                        checkBoxIsSaved.setChecked(isSaved);
                        setActiveVisibility();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        setActiveVisibility();
                    }
                });
    }

    private void setActiveVisibility() {
        loading.setVisibility(View.INVISIBLE);
        nameField.setVisibility(View.VISIBLE);
        riddleField.setVisibility(View.VISIBLE);
        if(showAnswer.isChecked()){
            answerField.setVisibility(View.VISIBLE);
        } else {
            answerField.setVisibility(View.INVISIBLE);
        }
        checkBoxIsSaved.setVisibility(View.VISIBLE);
        showAnswer.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
    }

    private void setLoadingVisibility() {
        loading.setVisibility(View.VISIBLE);
        nameField.setVisibility(View.INVISIBLE);
        riddleField.setVisibility(View.INVISIBLE);
        answerField.setVisibility(View.INVISIBLE);
        checkBoxIsSaved.setVisibility(View.INVISIBLE);
        showAnswer.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
    }

    private void wireWidgets() {
        nameField = rootView.findViewById(R.id.textView_viewriddle_name);
        riddleField = rootView.findViewById(R.id.textView_viewriddle_riddle);
        answerField = rootView.findViewById(R.id.textView_viewriddle_answer);
        checkBoxIsSaved = rootView.findViewById(R.id.checkBox_viewriddle_saved);
        showAnswer = rootView.findViewById(R.id.switch_viewriddle_revealanswer);
        loading = rootView.findViewById(R.id.progressBar_viewriddle_loadingBar);
        backButton = rootView.findViewById(R.id.button_viewriddle_back);
    }

    private void setListeners() {
        checkBoxIsSaved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && !isSaved) {
                    setLoadingVisibility();
                    List<Riddle> riddleAsList = new ArrayList<>();
                    riddleAsList.add(riddle);
                    Backendless.Persistence.of(BackendlessUser.class).addRelation(Backendless.UserService
                                    .CurrentUser(), "savedRiddles", riddleAsList, new AsyncCallback<Integer>() {
                                @Override
                                public void handleResponse(Integer response) {
                                    isSaved = true;
                                    setActiveVisibility();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    checkBoxIsSaved.setChecked(false);
                                    setActiveVisibility();
                                }
                            }
                    );
                } else if(!isChecked && isSaved) {
                    setLoadingVisibility();
                    List<Riddle> riddleAsList = new ArrayList<>();
                    riddleAsList.add(riddle);
                    Backendless.Persistence.of(BackendlessUser.class).deleteRelation(Backendless.UserService
                                    .CurrentUser(), "savedRiddles", riddleAsList, new AsyncCallback<Integer>() {
                                @Override
                                public void handleResponse(Integer response) {
                                    isSaved = false;
                                    setActiveVisibility();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    checkBoxIsSaved.setChecked(true);
                                    setActiveVisibility();
                                }
                            }
                    );
                }
            }
        });
        showAnswer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    answerField.setVisibility(View.VISIBLE);
                } else {
                    answerField.setVisibility(View.INVISIBLE);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDiscovered) {
                    Navigation.findNavController(v).navigate(R.id.action_viewRiddleFragment_to_discoverFragment);
                } else {
                    Navigation.findNavController(v).navigate(R.id.action_viewRiddleFragment_to_savedRiddlesFragment);
                }
            }
        });
    }
}
