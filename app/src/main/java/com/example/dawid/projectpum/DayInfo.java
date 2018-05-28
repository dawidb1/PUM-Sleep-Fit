package com.example.dawid.projectpum;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DayInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_info);
        ButterKnife.bind(this);
    }

    //region snoozeDialog
    @OnClick(R.id.snoreInfo)
    void createDialog(){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(DayInfo.this);
// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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
    @OnClick(R.id.mood_button)
    void checkMoodRadio(){
        uncheckAllMoodRadio();
        moodRadio.toggle();
    }
    @OnClick(R.id.neutral_button)
    void checkNeutralRadio(){
        uncheckAllMoodRadio();
        neutralRadio.toggle();
    }
    @OnClick(R.id.mood_bad_button)
    void checkMoodBadRadio(){
        uncheckAllMoodRadio();
        moodBadRadio.toggle();
    }

    //RadioButtons mood onClick
    @OnClick(R.id.mood_radioButton)
    void setMoodRadio(){
        checkMoodRadio();
    }
    @OnClick(R.id.neutral_radioButton)
    void setNeutralRadio(){
        checkNeutralRadio();
    }
    @OnClick(R.id.mood_bad_radioButton)
    void setMoodBadRadio(){
        checkMoodBadRadio();
    }
    //endregion

    //region energy onClick
    //Buttons energy onClick
    @OnClick(R.id.min_energy_btn)
    void checkMinEnergyRadio(){
        uncheckAllEnergyRadio();
        minEnergyRadio.toggle();
    }
    @OnClick(R.id.medium_energy_btn)
    void checkMediumEnergyRadio(){
        uncheckAllEnergyRadio();
        mediumEnergyRadio.toggle();
    }
    @OnClick(R.id.max_energy_btn)
    void checkMaxEnergyRadio(){
        uncheckAllEnergyRadio();
        maxEnergyRadio.toggle();
    }

    //RadioButtons energy onClick
    @OnClick(R.id.min_energy_radiobtn)
    void setMinEnergyRadio(){
        checkMinEnergyRadio();
    }
    @OnClick(R.id.medium_energy_radiobtn)
    void setMediumEnergyRadio(){
        checkMediumEnergyRadio();
    }
    @OnClick(R.id.max_energy_radiobtn)
    void setMaxEnergyRadio(){
        checkMaxEnergyRadio();
    }

    @OnClick(R.id.next_button) void goNext(){
        Intent intent = new Intent(DayInfo.this,PhysPsychActivity.class);
        startActivity(intent);
    }
    //endregion

    //region uncheck radiobuttons
    private void uncheckAllMoodRadio(){
        moodRadio.setChecked(false);
        neutralRadio.setChecked(false);
        moodBadRadio.setChecked(false);
    }
    private void uncheckAllEnergyRadio(){
        minEnergyRadio.setChecked(false);
        mediumEnergyRadio.setChecked(false);
        maxEnergyRadio.setChecked(false);
    }
    //endregion



    //region bind controls
    //bind mood RadioButtons
    @BindView(R.id.mood_radioButton)
    RadioButton moodRadio;
    @BindView(R.id.neutral_radioButton)
    RadioButton neutralRadio;
    @BindView(R.id.mood_bad_radioButton)
    RadioButton moodBadRadio;
    //energy RadioButtons
    @BindView(R.id.min_energy_radiobtn)
    RadioButton minEnergyRadio;
    @BindView(R.id.medium_energy_radiobtn)
    RadioButton mediumEnergyRadio;
    @BindView(R.id.max_energy_radiobtn)
    RadioButton maxEnergyRadio;

    //bind mood Buttons
    @BindView(R.id.mood_button)
    Button moodBtn;
    @BindView(R.id.neutral_button)
    Button neutralBtn;
    @BindView(R.id.mood_bad_button)
    Button moodBadBtn;
    //energy Buttons
    @BindView(R.id.min_energy_btn)
    Button minEnergyBtn;
    @BindView(R.id.medium_energy_btn)
    Button mediumEnergyBtn;
    @BindView(R.id.max_energy_btn)
    Button maxEnergyBtn;

    @BindView(R.id.snoreInfo)
    Button snoreInfo;
    //bind nextButton
    @BindView(R.id.next_button)
    Button nextButton;
    //endregion
}
