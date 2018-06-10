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
import android.view.View;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.DietAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsAdapter;
import com.example.dawid.projectpum.DAL.Adapters.LiquidsCounterAdapter;
import com.example.dawid.projectpum.DAL.CheckboxesEnums;
import com.example.dawid.projectpum.DAL.InstanceSaves.CsvModel;
import com.example.dawid.projectpum.DAL.InstanceSaves.ICsvHandler;
import com.example.dawid.projectpum.DAL.PhysicalItemVM;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.LayoutManager;
import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.DAL.CheckboxesEnums.*;
import static com.example.dawid.projectpum.R.id;
import static com.example.dawid.projectpum.R.id.liquids_recyclerView;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.id.number_picker;
import static com.example.dawid.projectpum.R.layout;
import static com.example.dawid.projectpum.R.layout.activity_fruits_vegetables;

public class FruitsVegetables extends AppCompatActivity implements ICsvHandler {

    public CsvModel model = null;
    public SharedPreferences shared = null;
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
        model = new CsvModel();
        shared = getSharedPreferences("csv_model", MODE_PRIVATE);
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
        model.Fruits = vegetablesNP.getValue();
        model.Vegetables = vegetablesNP.getValue();
        model.Coffee = numberpickerStates.get(Liquids.COFFEE.ordinal());
        model.Tea = numberpickerStates.get(Liquids.TEA.ordinal());
        model.Alcohol = numberpickerStates.get(Liquids.ALCOHOL.ordinal());
        model.EnergyDrinks = numberpickerStates.get(Liquids.ENERGY_DRINKS.ordinal());
    }

    @Override
    public void saveSharedPrefFromModel() {
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("fruits", model.Fruits);
        editor.putInt("vegetables",model.Vegetables);
        editor.putInt("coffee",model.Coffee);
        editor.putInt("tea",model.Tea);
        editor.putInt("alcohol",model.Alcohol);
        editor.putInt("energyDrinks",model.EnergyDrinks);
        editor.apply();
    }

    @Override
    public void saveStateFromModel(Bundle outState) {

    }
}
