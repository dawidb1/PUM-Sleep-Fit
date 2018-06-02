package com.example.dawid.projectpum;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.DietAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsCounterAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.LayoutManager;
import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.R.id;
import static com.example.dawid.projectpum.R.id.liquids_recyclerView;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.layout;
import static com.example.dawid.projectpum.R.layout.activity_fruits_vegetables;

public class FruitsVegetables extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_fruits_vegetables);
        bind(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        LiquidsCounterAdapter adapter = new LiquidsCounterAdapter();
        recyclerView.setAdapter(adapter);
    }

    @OnClick(next_button)
    void goNext() {
        Intent intent = new Intent(FruitsVegetables.this, DaySummary.class);
        startActivity(intent);
    }

    @BindView(liquids_recyclerView)
    RecyclerView recyclerView;
    @BindView(next_button)
    FloatingActionButton nextButton;
}
