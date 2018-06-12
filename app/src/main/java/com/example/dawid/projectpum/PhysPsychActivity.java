package com.example.dawid.projectpum;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.dawid.projectpum.DAL.Adapters.PhysicalAdapter;
import com.example.dawid.projectpum.DAL.Helpers.ScoreModel;
import com.example.dawid.projectpum.DAL.InstanceSaves.CsvModel;
import com.example.dawid.projectpum.DAL.InstanceSaves.ICsvHandler;
import com.example.dawid.projectpum.DAL.PhysicalItemVM;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.DialogInterface.OnClickListener;
import static android.support.v7.app.AlertDialog.Builder;
import static android.support.v7.widget.RecyclerView.LayoutManager;
import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.DAL.CheckboxesEnums.SportActivity;
import static com.example.dawid.projectpum.DAL.CheckboxesEnums.SportTime;
import static com.example.dawid.projectpum.DAL.CheckboxesEnums.SportValues;
import static com.example.dawid.projectpum.R.id;
import static com.example.dawid.projectpum.R.id.add_sport_name;
import static com.example.dawid.projectpum.R.id.add_sport_time;
import static com.example.dawid.projectpum.R.id.add_sport_value;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.id.physical_item_recycle_view;
import static com.example.dawid.projectpum.R.layout.activity_phys_psych;
import static com.example.dawid.projectpum.R.string.dialog_message;
import static com.example.dawid.projectpum.R.string.dialog_title;
import static com.example.dawid.projectpum.R.string.ok;
import static java.util.EnumSet.allOf;

public class PhysPsychActivity extends AppCompatActivity implements ICsvHandler {

    ArrayList<PhysicalItemVM> arrayList;
    public CsvModel model = null;
    public SharedPreferences csvShared = null;
    public SharedPreferences scoreShared = null;
    int scoreTime;
    int scoreIntensivity;
    int allScore;
    boolean noActivityAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_phys_psych);
        bind(this);
        init();

        loadActionBar();
        RefreshRecycleView();
        SetButtonsDefaultValue();
    }
    void init(){
        model = new CsvModel();
        csvShared = getSharedPreferences("csv_model", MODE_PRIVATE);
        scoreShared = getSharedPreferences("scoreShared", MODE_PRIVATE);
        scoreTime = 0;
        scoreIntensivity = 0;
        allScore = 0;
        arrayList = new ArrayList<>();
    }
    void loadActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().getCustomView();
    }

    void RefreshRecycleView() {
        LayoutManager physicalLayoutManager = new LinearLayoutManager(getApplicationContext());
        physicalRecyclerView.setLayoutManager(physicalLayoutManager);
        PhysicalAdapter physicalAdapter = new PhysicalAdapter(arrayList);
        physicalRecyclerView.setAdapter(physicalAdapter);
    }

    @OnClick(id.addActivity)
    void setAddActivity() {
        if (AreButtonsSets()) {
            PhysicalItemVM item = new PhysicalItemVM(addSportName.getText().toString(), addSportValue.getText().toString(), addSportTime.getText().toString());
            arrayList.add(item);
            RefreshRecycleView();
            SetButtonsDefaultValue();
            allScore += scoreTime + scoreIntensivity;
            Log.i("allScore",allScore+"");

            scoreIntensivity = 0;
            scoreTime = 0;
            noActivityAlert = true;
        }
        else {
            Toast.makeText(this,"Uzupełnij aktywność!",Toast.LENGTH_SHORT).show();
        }
    }

    String ActivitiesToJson(){
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        return json;
    }
    void SetButtonsDefaultValue() {
        addSportName.setText("dodaj");
        addSportValue.setText("wybierz");
        addSportTime.setText("0");
    }

    boolean AreButtonsSets() {
        if (addSportName.getText() != "dodaj" &&
                addSportValue.getText() != "wybierz" &&
                addSportTime.getText() != 0 + "") {
            return true;
        } else {
            return false;
        }
    }

    //    region sports dialogs
    @OnClick(add_sport_name)
    void chooseSportName() {
        Builder builder = new Builder(PhysPsychActivity.this);
        builder.setTitle("Wybierz dzisiejszą aktywność");

        ArrayList<SportActivity> enumList = new ArrayList<SportActivity>(allOf(SportActivity.class));
        final String[] enumArray = new String[enumList.size()];
        int i = 0;
        for (SportActivity item : enumList) {
            enumArray[i] = item.getName();
            i++;
        }

        builder.setItems(enumArray, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addSportName.setText(enumArray[which]);
                chooseSportValue();
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(add_sport_value)
    void chooseSportValue() {
        Builder builder = new Builder(PhysPsychActivity.this);
        builder.setTitle("Wybierz intensywność");

        ArrayList<SportValues> enumList = new ArrayList<SportValues>(allOf(SportValues.class));
        final String[] enumArray = new String[enumList.size()];
        int i = 0;
        for (SportValues item : enumList) {
            enumArray[i] = item.getName();
            i++;
        }

        builder.setItems(enumArray, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addSportValue.setText(enumArray[which]);
                String intensivity =  enumArray[which];
                scoreIntensivity = ScoreModel.getActivityIntenseScore(intensivity);
                Log.i("scoreIntensivity",scoreIntensivity+"");
                chooseSportTime();
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(add_sport_time)
    void chooseSportTime() {
        Builder builder = new Builder(PhysPsychActivity.this);
        builder.setTitle("Wybierz czas");

        ArrayList<SportTime> enumList = new ArrayList<SportTime>(allOf(SportTime.class));
        final String[] enumArray = new String[enumList.size()];
        int i = 0;
        for (SportTime item : enumList) {
            enumArray[i] = item.getName();
            i++;
        }

        builder.setItems(enumArray, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addSportTime.setText(enumArray[which]);
                int time = Integer.parseInt(enumArray[which]);
                scoreTime = ScoreModel.getActivityTimeScore(time);
                Log.i("scoreTime",scoreTime+"");
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
//    endregion


    @OnClick(next_button)
    void goNext() {
        if (noActivityAlert){
            fillModel();
            saveSharedPrefFromModel();
            Intent intent = new Intent(PhysPsychActivity.this, FruitsVegetables.class);
            startActivity(intent);
        }
        else {
            // 1. Instantiate an AlertDialog.Builder with its constructor
            Builder builder = new Builder(this);
// 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Pamiętaj, że możesz dodać swoje dzisiejsze aktywności :)")
                    .setTitle("Dodaj aktywność");
            builder.setPositiveButton(ok, new OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    noActivityAlert = true;
                    // User clicked OK button
                }
            });

// 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }


    }

    @BindView(id.addActivity)
    FloatingActionButton addActivity;
    @BindView(add_sport_name)
    Button addSportName;
    @BindView(add_sport_value)
    Button addSportValue;
    @BindView(add_sport_time)
    Button addSportTime;

    @BindView(physical_item_recycle_view)
    RecyclerView physicalRecyclerView;

    @BindView(next_button)
    FloatingActionButton nextButton;

    @Override
    public void fillModel() {
        model.Activities = ActivitiesToJson();
        model.ActivitiesScore = allScore;
    }

    @Override
    public void saveSharedPrefFromModel() {
        SharedPreferences.Editor csvEditor = csvShared.edit();
        csvEditor.putString("activities", model.Activities);
        Log.i("csvModel.activitiesJson",model.Activities);
        csvEditor.apply();

        SharedPreferences.Editor scoreEditor = scoreShared.edit();
        scoreEditor.putInt("activitiesScore",model.ActivitiesScore);
        scoreEditor.apply();
    }

    @Override
    public void saveStateFromModel(Bundle outState) {
        outState.putString("activities", model.Activities);
    }
}
