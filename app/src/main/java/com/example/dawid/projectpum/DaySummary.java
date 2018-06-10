package com.example.dawid.projectpum;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.StarsAdapter;
import com.example.dawid.projectpum.DAL.Helpers.EasyConverter;
import com.example.dawid.projectpum.DAL.InstanceSaves.CsvEnums;
import com.example.dawid.projectpum.DAL.InstanceSaves.CsvModel;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaySummary extends AppCompatActivity {

    CsvModel model = null;
    SharedPreferences prefs = null;
    String FILE_NAME = "results.csv";

    public int Rating;
    static int maxRating = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_summary);
        ButterKnife.bind(this);
        init();
        loadActionBar();

        fillModel();
        Rating = model.ActivitiesScore%maxRating;
        Log.i("ActivitiesScore",model.ActivitiesScore + "");
        Log.i("Rating",Rating + "");
        loadRecycleView();
    }
    void init(){
        prefs = getSharedPreferences("csv_model", MODE_PRIVATE);
        model = new CsvModel();
        Rating = 0;
    }
    void loadRecycleView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        StarsAdapter adapter = new StarsAdapter(Rating);
        recyclerView.setAdapter(adapter);
    }
    void loadActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().getCustomView();
    }

    public void fillModel(){
        model.StartSleepString = prefs.getString("startSleep","");
        model.EndSleepString = prefs.getString("endSleep","");
        model.StepsString =  prefs.getString("stepsString","999");
        model.Mood = CsvEnums.Mood.values()[prefs.getInt("mood",99)];
        model.Energy = CsvEnums.Energy.values()[prefs.getInt("energy",99)];
        model.Snore = prefs.getBoolean("snore",false);
        model.Activities = prefs.getString("activities","noname");
        model.ActivitiesScore = prefs.getInt("activitiesScore",999);
        model.Fruits = prefs.getInt("fruits",999);
        model.Vegetables = prefs.getInt("vegetables",999);
        model.Coffee = prefs.getInt("coffee",999);
        model.Tea = prefs.getInt("tea",999);
        model.Alcohol = prefs.getInt("alcohol",999);
        model.EnergyDrinks = prefs.getInt("energyDrinks",999);
    }

    void writeModelToCsv(){
        File path = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS);
        File readyFile = new File(path, FILE_NAME);

        CSVWriter writer = null;

        List<String[]> data = new ArrayList<String[]>();

        int x = prefs.getInt("steps", 0);

        if (readyFile.length() == 0){
            data.add(model.Headers);
        }
        data.add(new String[] {
                model.StartSleepString,
                model.EndSleepString,
                model.StepsString,
                model.Mood.name(),
                model.Energy.name(),
                EasyConverter.BoolToString(model.Snore),
                model.Activities,
                model.Fruits+"",
                model.Vegetables+"",
                model.Coffee +  "",
                model.Tea+"",
                model.Alcohol+"",
                model.EnergyDrinks+""
                });

        try {
            writer = new CSVWriter(new FileWriter(readyFile,true));
            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.exit_button) void exitApp(){
        writeModelToCsv();
        this.finishAffinity();
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.exit_button)
    Button exitButton;
}
