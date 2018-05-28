package com.example.dawid.projectpum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.JobAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LifeAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobLifeAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_life_about);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager jobLayoutManager = new LinearLayoutManager(getApplicationContext());
        jobRecycleView.setLayoutManager(jobLayoutManager);
        JobAdapter jobAdapter = new JobAdapter();
        jobRecycleView.setAdapter(jobAdapter);

        RecyclerView.LayoutManager lifeLayoutManager = new LinearLayoutManager(getApplicationContext());
        lifeRecycleView.setLayoutManager(lifeLayoutManager);
        LifeAdapter lifeAdapter = new LifeAdapter();
        lifeRecycleView.setAdapter(lifeAdapter);
    }

    @OnClick(R.id.next_button) void goNext(){
        Intent intent = new Intent(JobLifeAbout.this,CommonBandSync.class);
        startActivity(intent);
    }
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.jobAbout_recycleView)
    RecyclerView jobRecycleView;
    @BindView(R.id.lifeAbout_recycleView)
    RecyclerView lifeRecycleView;
}
