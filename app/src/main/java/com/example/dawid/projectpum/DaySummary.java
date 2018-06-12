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
import android.widget.TextView;

import com.example.dawid.projectpum.DAL.Adapters.StarsAdapter;
import com.example.dawid.projectpum.DAL.Helpers.EasyConverter;
import com.example.dawid.projectpum.DAL.Helpers.ScoreModel;
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

    CsvModel csvModel = null;
    ScoreModel scoreModel = null;
    SharedPreferences csvShared = null;
    SharedPreferences scoreShared = null;
    String FILE_NAME = "results.csv";

    public int Rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_summary);
        ButterKnife.bind(this);
        init();
        loadActionBar();

        fillModel();
        loadRating();
        loadRecycleView();
    }
    void init(){
        csvShared = getSharedPreferences("csv_model", MODE_PRIVATE);
        scoreShared = getSharedPreferences("scoreShared",MODE_PRIVATE);
        csvModel = new CsvModel();
        scoreModel = new ScoreModel();
        Rating = 0;
    }

    void loadRating(){
        scoreModel.setFullScore(scoreModel);
        Log.i("FullScore",scoreModel.FullScore+"");
        scoreModel.setRating(scoreModel.FullScore);

        Rating = scoreModel.Rating;
        Log.i("Rating",Rating+"");
        ratingText.setText(Rating+"/5");
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
        csvModel.StartSleepString = csvShared.getString("startSleep","");
        csvModel.EndSleepString = csvShared.getString("endSleep","");
        csvModel.StepsString =  csvShared.getString("stepsString","999");
        csvModel.Mood = CsvEnums.Mood.values()[csvShared.getInt("mood",99)];
        csvModel.Energy = CsvEnums.Energy.values()[csvShared.getInt("energy",99)];
        csvModel.Snore = csvShared.getBoolean("snore",false);
        csvModel.Activities = csvShared.getString("activities","noname");
        csvModel.Fruits = csvShared.getInt("fruits",999);
        csvModel.Vegetables = csvShared.getInt("vegetables",999);
        csvModel.Coffee = csvShared.getInt("coffee",999);
        csvModel.Tea = csvShared.getInt("tea",999);
        csvModel.Alcohol = csvShared.getInt("alcohol",999);
        csvModel.EnergyDrinks = csvShared.getInt("energyDrinks",999);

        scoreModel.ActivitiesScore = scoreShared.getInt("activitiesScore",999);
        scoreModel.FruitAndVegetableScore = scoreShared.getInt("fruitAndVegetableScore",999);
        scoreModel.SnoreScore = scoreShared.getInt("snoreScore",999);
    }

    void writeModelToCsv(){
        File path = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS);
        File readyFile = new File(path, FILE_NAME);

        CSVWriter writer = null;

        List<String[]> data = new ArrayList<String[]>();

        int x = csvShared.getInt("steps", 0);

        if (readyFile.length() == 0){
            data.add(csvModel.Headers);
        }
        data.add(new String[] {
                csvModel.StartSleepString,
                csvModel.EndSleepString,
                csvModel.StepsString,
                csvModel.Mood.name(),
                csvModel.Energy.name(),
                EasyConverter.BoolToString(csvModel.Snore),
                csvModel.Activities,
                csvModel.Fruits+"",
                csvModel.Vegetables+"",
                csvModel.Coffee +  "",
                csvModel.Tea+"",
                csvModel.Alcohol+"",
                csvModel.EnergyDrinks+""
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

    @BindView(R.id.rating_text)
    TextView ratingText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.exit_button)
    Button exitButton;
}
