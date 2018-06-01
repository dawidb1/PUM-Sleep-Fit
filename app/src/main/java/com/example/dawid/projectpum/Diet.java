package com.example.dawid.projectpum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.example.dawid.projectpum.DAL.Adapters.DietAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Diet extends AppCompatActivity {

    SharedPreferences dietShared = null;

    private ArrayList<Boolean> isDietSelected;
    private ArrayList<Boolean> isLiquidsSelected;

    DietAdapter dietAdapter = null;
    LiquidsAdapter liquidsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        ButterKnife.bind(this);

        dietShared = getSharedPreferences("diet", Context.MODE_PRIVATE);
        String jsonDiet = dietShared.getString("dietBool","");
        String jsonLiquids = dietShared.getString("liquidsBool","");
        Gson googleJson = new Gson();
        isDietSelected = googleJson.fromJson(jsonDiet, ArrayList.class);
        isLiquidsSelected = googleJson.fromJson(jsonLiquids, ArrayList.class);

        RecyclerView.LayoutManager dietLayoutManager = new LinearLayoutManager(getApplicationContext());
        dietRecycleView.setLayoutManager(dietLayoutManager);
        dietAdapter = new DietAdapter(isDietSelected);
        dietRecycleView.setAdapter(dietAdapter);

        RecyclerView.LayoutManager liquidsLayoutManager = new LinearLayoutManager(getApplicationContext());
        liquidsRecycleView.setLayoutManager(liquidsLayoutManager);
        liquidsAdapter = new LiquidsAdapter(isLiquidsSelected);
        liquidsRecycleView.setAdapter(liquidsAdapter);
    }

    @OnClick(R.id.next_button) void goNext(){

        String dietBoolJson = new Gson().toJson(dietAdapter.IsSelected);
        String liquidsBoolJson = new Gson().toJson(liquidsAdapter.IsSelected);

        SharedPreferences.Editor dietEditor = dietShared.edit();

        dietEditor.putString("dietBool",dietBoolJson);
        dietEditor.putString("liquidsBool",liquidsBoolJson);
        dietEditor.apply();

//        String tester = dietShared.getString("dietBool","");
//        Toast.makeText(this, tester,Toast.LENGTH_LONG).show();

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
