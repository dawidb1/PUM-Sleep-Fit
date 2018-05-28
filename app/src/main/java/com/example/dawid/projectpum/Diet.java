package com.example.dawid.projectpum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.DietAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsAdapter;
import com.example.dawid.projectpum.DAL.FileHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Diet extends AppCompatActivity {

    private ArrayList<String> ListToExport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager dietLayoutManager = new LinearLayoutManager(getApplicationContext());
        dietRecycleView.setLayoutManager(dietLayoutManager);
        DietAdapter dietAdapter = new DietAdapter();
        dietRecycleView.setAdapter(dietAdapter);
        ListToExport = dietAdapter.SelectedItems;

        RecyclerView.LayoutManager liquidsLayoutManager = new LinearLayoutManager(getApplicationContext());
        liquidsRecycleView.setLayoutManager(liquidsLayoutManager);
        LiquidsAdapter liquidsAdapter = new LiquidsAdapter();
        liquidsRecycleView.setAdapter(liquidsAdapter);
    }

    @OnClick(R.id.next_button) void goNext(){
        FileHelper.PrintToFile(ListToExport);
        Intent intent = new Intent(Diet.this,JobLifeAbout.class);
        startActivity(intent);
    }

    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.diet_recycle_view)
    RecyclerView dietRecycleView;
    @BindView(R.id.liquids_recycle_view)
    RecyclerView liquidsRecycleView;
}
