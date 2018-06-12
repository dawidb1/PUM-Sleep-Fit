package com.example.dawid.projectpum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dawid.projectpum.DAL.Adapters.LiquidsCounterAdapter;
import com.example.dawid.projectpum.DAL.Helpers.ScoreModel;
import com.example.dawid.projectpum.DAL.InstanceSaves.CsvModel;
import com.example.dawid.projectpum.DAL.InstanceSaves.ICsvHandler;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.LayoutManager;
import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.DAL.CheckboxesEnums.*;
import static com.example.dawid.projectpum.R.id;
import static com.example.dawid.projectpum.R.id.liquids_recyclerView;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.layout.activity_fruits_vegetables;

public class FruitsVegetables extends AppCompatActivity implements ICsvHandler {

    public CsvModel csvModel = null;
    public ScoreModel scoreModel = null;
    public SharedPreferences csvShared = null;
    public SharedPreferences scoreShared = null;
    ArrayList<Integer> numberpickerStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_fruits_vegetables);
        bind(this);

        init();
        loadActionBar();
        loadRecyclerViews();

    }
    void init(){
        csvModel = new CsvModel();
        scoreModel = new ScoreModel();
        csvShared = getSharedPreferences("csv_model", MODE_PRIVATE);
        scoreShared = getSharedPreferences("scoreShared", MODE_PRIVATE);
        numberpickerStates = new ArrayList<>();
    }
    void loadActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().getCustomView();
    }
    void loadRecyclerViews(){
        LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        LiquidsCounterAdapter adapter = new LiquidsCounterAdapter(numberpickerStates);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(next_button)
    void goNext() {
        fillModel();
        saveSharedPrefFromModel();
        Intent intent = new Intent(FruitsVegetables.this, DaySummary.class);
        startActivity(intent);
    }

    @BindView(liquids_recyclerView)
    RecyclerView recyclerView;
    @BindView(next_button)
    FloatingActionButton nextButton;
    @BindView(id.vegetables_numberPicker)
    NumberPicker vegetablesNP;
    @BindView(id.food_numberPicker)
    NumberPicker foodNP;

    @Override
    public void fillModel() {
        csvModel.Fruits = foodNP.getValue();
        csvModel.Vegetables = vegetablesNP.getValue();
        csvModel.Coffee = numberpickerStates.get(Liquids.COFFEE.ordinal());
        csvModel.Tea = numberpickerStates.get(Liquids.TEA.ordinal());
        csvModel.Alcohol = numberpickerStates.get(Liquids.ALCOHOL.ordinal());
        csvModel.EnergyDrinks = numberpickerStates.get(Liquids.ENERGY_DRINKS.ordinal());

        scoreModel.setFruitsScore(csvModel.Fruits);
        scoreModel.setVegetablesScore(csvModel.Vegetables);
        scoreModel.setCoffeeScore(csvModel.Coffee);
        scoreModel.setTeaScore(csvModel.Tea);
        scoreModel.setAlcoholScore(csvModel.Alcohol);
        scoreModel.setEnergyDrinksScore(csvModel.EnergyDrinks);

        scoreModel.setFruitAndVegetableScore(scoreModel);
        Log.i("Fruits&Drinks score",scoreModel.FruitAndVegetableScore+"");
    }

    @Override
    public void saveSharedPrefFromModel() {
        SharedPreferences.Editor csvEditor = csvShared.edit();
        csvEditor.putInt("fruits", csvModel.Fruits);
        csvEditor.putInt("vegetables", csvModel.Vegetables);
        csvEditor.putInt("coffee", csvModel.Coffee);
        csvEditor.putInt("tea", csvModel.Tea);
        csvEditor.putInt("alcohol", csvModel.Alcohol);
        csvEditor.putInt("energyDrinks", csvModel.EnergyDrinks);
        csvEditor.apply();

        SharedPreferences.Editor scoreEditor = scoreShared.edit();
        scoreEditor.putInt("fruitAndVegetableScore", scoreModel.FruitAndVegetableScore);
        scoreEditor.apply();
    }

    @Override
    public void saveStateFromModel(Bundle outState) {

    }
}
