package com.example.dawid.projectpum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.DietAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsAdapter;
import com.example.dawid.projectpum.DAL.Adapters.SportActivityAdapter;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.SharedPreferences.Editor;
import static android.support.v7.widget.RecyclerView.LayoutManager;
import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.R.id;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.id.sportActivity_recycle_view;
import static com.example.dawid.projectpum.R.layout;
import static com.example.dawid.projectpum.R.layout.activity_setup_sport_activities;

public class SetupSportActivities extends AppCompatActivity {

    SharedPreferences shared = null;
    private ArrayList<Boolean> isSelected;
    SportActivityAdapter sportActivityAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_setup_sport_activities);
        bind(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        shared = getSharedPreferences("sport", MODE_PRIVATE);
        String json = shared.getString("sportBool", "");
        Gson googleJson = new Gson();
        isSelected = googleJson.fromJson(json, ArrayList.class);

        LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        sportActivityRecycleView.setLayoutManager(layoutManager);
        sportActivityAdapter = new SportActivityAdapter(isSelected);
        sportActivityRecycleView.setAdapter(sportActivityAdapter);
    }

    @OnClick(next_button)
    void goNext() {
        String boolJson = new Gson().toJson(sportActivityAdapter.IsSelected);

        Editor editor = shared.edit();

        editor.putString("sportBool", boolJson);
        editor.apply();

        //CSV WRITER

        //        File path = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DOWNLOADS);
//        File sdCardFile = new File(path, "piesel.csv");
//
//        CSVWriter writer = null;
//        Log.i("cokolwiek",path.getAbsolutePath());
//
//        List<String[]> data = new ArrayList<String[]>();
//        data.add(new String[] {"India", "New Delhi"});
//        data.add(new String[] {"United States", "Washington D.C"});
//        data.add(new String[] {"Germany", "Berlin"});
//
//        try {
//            writer = new CSVWriter(new FileWriter(sdCardFile));
//            writer.writeAll(data);
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } ss

        Intent intent = new Intent(SetupSportActivities.this, Diet.class);
        startActivity(intent);
    }

    @BindView(next_button)
    FloatingActionButton nextButton;
    @BindView(sportActivity_recycle_view)
    RecyclerView sportActivityRecycleView;

}
