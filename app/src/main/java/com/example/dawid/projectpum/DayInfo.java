package com.example.dawid.projectpum;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dawid.projectpum.DAL.InstanceSaves.CsvEnums;
import com.example.dawid.projectpum.DAL.InstanceSaves.CsvModel;
import com.example.dawid.projectpum.DAL.InstanceSaves.ICsvHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.DialogInterface.OnClickListener;
import static android.support.v7.app.AlertDialog.Builder;
import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.DAL.InstanceSaves.CsvEnums.*;
import static com.example.dawid.projectpum.R.id;
import static com.example.dawid.projectpum.R.id.max_energy_btn;
import static com.example.dawid.projectpum.R.id.max_energy_radiobtn;
import static com.example.dawid.projectpum.R.id.medium_energy_btn;
import static com.example.dawid.projectpum.R.id.medium_energy_radiobtn;
import static com.example.dawid.projectpum.R.id.min_energy_btn;
import static com.example.dawid.projectpum.R.id.min_energy_radiobtn;
import static com.example.dawid.projectpum.R.id.mood_bad_button;
import static com.example.dawid.projectpum.R.id.mood_bad_radioButton;
import static com.example.dawid.projectpum.R.id.mood_button;
import static com.example.dawid.projectpum.R.id.mood_radioButton;
import static com.example.dawid.projectpum.R.id.neutral_button;
import static com.example.dawid.projectpum.R.id.neutral_radioButton;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.layout;
import static com.example.dawid.projectpum.R.layout.activity_day_info;
import static com.example.dawid.projectpum.R.string;
import static com.example.dawid.projectpum.R.string.cancel;
import static com.example.dawid.projectpum.R.string.dialog_message;
import static com.example.dawid.projectpum.R.string.dialog_title;
import static com.example.dawid.projectpum.R.string.ok;

public class DayInfo extends AppCompatActivity implements ICsvHandler {
    public CsvModel model = null;
    public SharedPreferences shared = null;

    private Mood moodClicked;
    private Energy energyClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_day_info);
        bind(this);
        init();
        loadActionBar();
    }
    void init(){
        model = new CsvModel();
        shared = getSharedPreferences("csv_model", MODE_PRIVATE);
    }
    void loadActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().getCustomView();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        fillModel();
        saveSharedPrefFromModel();
        saveStateFromModel(outState);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int state = savedInstanceState.getInt("mood",99);
        switch (Mood.values()[state]){
            case MOOD:
                moodRadio.setChecked(true);
                break;
            case NEUTRAL:
                neutralRadio.setChecked(true);
                break;
            case MOOD_BAD:
                moodBadRadio.setChecked(true);
                break;
        }
    }

    //region snoozeDialog
    @OnClick(id.snoreInfo)
    void createDialog() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        Builder builder = new Builder(DayInfo.this);
// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(dialog_message)
                .setTitle(dialog_title);
        builder.setPositiveButton(ok, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //endregion

    //region mood onClick

    //Buttons mood onClick
    @OnClick(mood_button)
    void checkMoodRadio() {
        moodClicked = Mood.MOOD;
        uncheckAllMoodRadio();
        moodRadio.toggle();
    }

    @OnClick(neutral_button)
    void checkNeutralRadio() {
        moodClicked = Mood.NEUTRAL;
        uncheckAllMoodRadio();
        neutralRadio.toggle();
    }

    @OnClick(mood_bad_button)
    void checkMoodBadRadio() {
        moodClicked = Mood.MOOD_BAD;
        uncheckAllMoodRadio();
        moodBadRadio.toggle();
    }

    //RadioButtons mood onClick
    @OnClick(mood_radioButton)
    void setMoodRadio() {
        checkMoodRadio();
    }

    @OnClick(neutral_radioButton)
    void setNeutralRadio() {
        checkNeutralRadio();
    }

    @OnClick(mood_bad_radioButton)
    void setMoodBadRadio() {
        checkMoodBadRadio();
    }
    //endregion

    //region energy onClick
    //Buttons energy onClick
    @OnClick(min_energy_btn)
    void checkMinEnergyRadio() {
        energyClicked = Energy.LOW;
        uncheckAllEnergyRadio();
        minEnergyRadio.toggle();
    }

    @OnClick(medium_energy_btn)
    void checkMediumEnergyRadio() {
        energyClicked = Energy.MID;
        uncheckAllEnergyRadio();
        mediumEnergyRadio.toggle();
    }

    @OnClick(max_energy_btn)
    void checkMaxEnergyRadio() {
        energyClicked = Energy.HIGH;
        uncheckAllEnergyRadio();
        maxEnergyRadio.toggle();
    }

    //RadioButtons energy onClick
    @OnClick(min_energy_radiobtn)
    void setMinEnergyRadio() {
        checkMinEnergyRadio();
    }

    @OnClick(medium_energy_radiobtn)
    void setMediumEnergyRadio() {
        checkMediumEnergyRadio();
    }

    @OnClick(max_energy_radiobtn)
    void setMaxEnergyRadio() {
        checkMaxEnergyRadio();
    }

    @OnClick(next_button)
    void goNext() {
        Intent intent = new Intent(DayInfo.this, PhysPsychActivity.class);
        startActivity(intent);
    }
    //endregion

    //region uncheck radiobuttons
    private void uncheckAllMoodRadio() {
        moodRadio.setChecked(false);
        neutralRadio.setChecked(false);
        moodBadRadio.setChecked(false);
    }

    private void uncheckAllEnergyRadio() {
        minEnergyRadio.setChecked(false);
        mediumEnergyRadio.setChecked(false);
        maxEnergyRadio.setChecked(false);
    }
    //endregion


    //region bind controls
    //bind mood RadioButtons
    @BindView(mood_radioButton)
    RadioButton moodRadio;
    @BindView(neutral_radioButton)
    RadioButton neutralRadio;
    @BindView(mood_bad_radioButton)
    RadioButton moodBadRadio;
    //energy RadioButtons
    @BindView(min_energy_radiobtn)
    RadioButton minEnergyRadio;
    @BindView(medium_energy_radiobtn)
    RadioButton mediumEnergyRadio;
    @BindView(max_energy_radiobtn)
    RadioButton maxEnergyRadio;

    //bind mood Buttons
    @BindView(mood_button)
    Button moodBtn;
    @BindView(neutral_button)
    Button neutralBtn;
    @BindView(mood_bad_button)
    Button moodBadBtn;
    //energy Buttons
    @BindView(min_energy_btn)
    Button minEnergyBtn;
    @BindView(medium_energy_btn)
    Button mediumEnergyBtn;
    @BindView(max_energy_btn)
    Button maxEnergyBtn;

    @BindView(id.snoreInfo)
    Button snoreInfo;
    @BindView(id.snore_checkbox)
    CheckBox snoreCheckbox;
    //bind nextButton

    @BindView(next_button)
    FloatingActionButton nextButton;

    @Override
    public void fillModel() {
        model.Mood =  moodClicked;
        model.Energy = energyClicked;
        model.Snore = snoreCheckbox.isChecked();
    }

    @Override
    public void saveSharedPrefFromModel() {
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("mood", model.Mood.ordinal());
        editor.putInt("energy", model.Energy.ordinal());
        editor.putInt("steps", model.Steps);
        editor.putBoolean("snore",model.Snore);
        editor.apply();
    }

    @Override
    public void saveStateFromModel(Bundle outState) {
        outState.putInt("mood", model.Mood.ordinal());
        outState.putInt("energy",  model.Energy.ordinal());
        outState.putInt("steps", model.Steps);
        outState.putBoolean("snore",model.Snore);
    }
    //endregion
}
