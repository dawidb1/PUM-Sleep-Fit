package com.example.dawid.projectpum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.LiquidsAdapter;
import com.example.dawid.projectpum.DAL.Adapters.SportActivityAdapter;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetupSportActivities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_sport_activities);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        sportActivityRecycleView.setLayoutManager(layoutManager);
        SportActivityAdapter sportActivityAdapter = new SportActivityAdapter();
        sportActivityRecycleView.setAdapter(sportActivityAdapter);
    }

    @OnClick(R.id.next_button) void goNext(){
        Intent intent = new Intent(SetupSportActivities.this,Diet.class);
        startActivity(intent);
    }
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.sportActivity_recycle_view)
    RecyclerView sportActivityRecycleView;

}
