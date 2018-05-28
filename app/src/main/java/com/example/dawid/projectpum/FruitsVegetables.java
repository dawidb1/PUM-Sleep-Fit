package com.example.dawid.projectpum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.DietAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsCounterAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FruitsVegetables extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits_vegetables);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        LiquidsCounterAdapter adapter = new LiquidsCounterAdapter();
        recyclerView.setAdapter(adapter);
    }
    @OnClick(R.id.next_button) void goNext(){
        Intent intent = new Intent(FruitsVegetables.this,DaySummary.class);
        startActivity(intent);
    }

    @BindView(R.id.liquids_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.next_button)
    Button nextButton;
}
