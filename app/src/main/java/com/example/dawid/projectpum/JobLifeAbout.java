package com.example.dawid.projectpum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.DietAdapter;
import com.example.dawid.projectpum.DAL.Adapters.JobAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LifeAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsAdapter;
import com.example.dawid.projectpum.DAL.Adapters.SportActivityAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.SharedPreferences.Editor;
import static android.support.v7.widget.RecyclerView.LayoutManager;
import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.R.id;
import static com.example.dawid.projectpum.R.id.jobAbout_recycleView;
import static com.example.dawid.projectpum.R.id.lifeAbout_recycleView;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.layout;
import static com.example.dawid.projectpum.R.layout.activity_job_life_about;

public class JobLifeAbout extends AppCompatActivity {

    SharedPreferences shared = null;

    private ArrayList<Boolean> isJobSelected;
    private ArrayList<Boolean> isLifeAboutSelected;

    JobAdapter jobAdapter = null;
    LifeAdapter lifeAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_job_life_about);
        bind(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        shared = getSharedPreferences("jobLife", MODE_PRIVATE);
        String jsonJob = shared.getString("jobBool", "");
        String jsonLife = shared.getString("lifeBool", "");
        Gson googleJson = new Gson();
        isJobSelected = googleJson.fromJson(jsonJob, ArrayList.class);
        isLifeAboutSelected = googleJson.fromJson(jsonLife, ArrayList.class);

        LayoutManager jobLayoutManager = new LinearLayoutManager(getApplicationContext());
        jobRecycleView.setLayoutManager(jobLayoutManager);
        jobAdapter = new JobAdapter(isJobSelected);
        jobRecycleView.setAdapter(jobAdapter);

        LayoutManager lifeLayoutManager = new LinearLayoutManager(getApplicationContext());
        lifeRecycleView.setLayoutManager(lifeLayoutManager);
        lifeAdapter = new LifeAdapter(isLifeAboutSelected);
        lifeRecycleView.setAdapter(lifeAdapter);
    }

    @OnClick(next_button)
    void goNext() {
        String jobBoolJson = new Gson().toJson(jobAdapter.IsSelected);
        String lifeBoolJson = new Gson().toJson(lifeAdapter.IsSelected);

        Editor editor = shared.edit();

        editor.putString("jobBool", jobBoolJson);
        editor.putString("lifeBool", lifeBoolJson);
        editor.apply();

        Intent intent = new Intent(JobLifeAbout.this, CommonBandSync.class);
        startActivity(intent);
    }

    @BindView(next_button)
    FloatingActionButton nextButton;
    @BindView(jobAbout_recycleView)
    RecyclerView jobRecycleView;
    @BindView(lifeAbout_recycleView)
    RecyclerView lifeRecycleView;
}
