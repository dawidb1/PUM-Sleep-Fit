package com.example.dawid.projectpum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class SetupSportActivities extends AppCompatActivity {

    SharedPreferences shared = null;
    private ArrayList<Boolean> isSelected;
    SportActivityAdapter sportActivityAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_sport_activities);
        ButterKnife.bind(this);

        shared = getSharedPreferences("sport", Context.MODE_PRIVATE);
        String json = shared.getString("sportBool","");
        Gson googleJson = new Gson();
        isSelected = googleJson.fromJson(json, ArrayList.class);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        sportActivityRecycleView.setLayoutManager(layoutManager);
        sportActivityAdapter = new SportActivityAdapter(isSelected);
        sportActivityRecycleView.setAdapter(sportActivityAdapter);
    }

    @OnClick(R.id.next_button) void goNext() {
        String boolJson = new Gson().toJson(sportActivityAdapter.IsSelected);

        SharedPreferences.Editor editor = shared.edit();

        editor.putString("sportBool",boolJson);
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

        Intent intent = new Intent(SetupSportActivities.this,Diet.class);
        startActivity(intent);
    }
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.sportActivity_recycle_view)
    RecyclerView sportActivityRecycleView;

}
