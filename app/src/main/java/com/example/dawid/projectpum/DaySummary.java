package com.example.dawid.projectpum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.DietAdapter;
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

    public int Rating = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_summary);
        ButterKnife.bind(this);
        prefs = getSharedPreferences("csv_model", MODE_PRIVATE);
        model = new CsvModel();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        StarsAdapter adapter = new StarsAdapter(Rating);
        recyclerView.setAdapter(adapter);
    }

    public void fillModel(){
        model.StartSleepString = prefs.getString("startSleep","");
        model.EndSleepString = prefs.getString("endSleep","");
        model.StepsString =  prefs.getString("stepsString","0");
        model.Mood = CsvEnums.Mood.values()[prefs.getInt("mood",0)];
        model.Energy = CsvEnums.Energy.values()[prefs.getInt("energy",0)];
        model.Snore = prefs.getBoolean("snore",false);
    }

    void saveCsv(){
        //try append to existing file
//        File path = Environment.getExternalStorageDirectory();
//        Log.i("path",path.getAbsolutePath());
//
//        File file = new File(path, "piesel.csv");
//
//        CSVWriter writer = null;
//
//        FileWriter mFileWriter = new FileWriter("piesel.csv",true);
//        CSVWriter mCsvWriter = new CSVWriter(mFileWriter);
        //if(file.exist())
        File path = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS);
        File sdCardFile = new File(path, "piesel.csv");

        CSVWriter writer = null;

        List<String[]> data = new ArrayList<String[]>();

        int x = prefs.getInt("steps", 0);
        String stepsString =   prefs.getString("stepsString","dupa");
        Log.i("stepsInt",x + "");
        Log.i("stepsString",stepsString);
        Log.i("energy",model.Energy.toString() + " " + model.Energy.ordinal());
        Log.i("model in toString",Integer.toString(model.Steps));


        data.add(model.Headers);
        data.add(new String[] {
                    model.StartSleepString,
                    model.EndSleepString,
                    model.StepsString,
                    model.Mood.name(),
                    model.Energy.toString(),
                    EasyConverter.BoolToString(model.Snore),
                });

        try {
            writer = new CSVWriter(new FileWriter(sdCardFile));
            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.exit_button) void exitApp(){
        fillModel();
        saveCsv();
        this.finishAffinity();
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.exit_button)
    Button exitButton;
}
