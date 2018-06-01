package com.example.dawid.projectpum;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.dawid.projectpum.DAL.Adapters.PhysicalAdapter;
import com.example.dawid.projectpum.DAL.CheckboxesEnums;
import com.example.dawid.projectpum.DAL.PhysicalItemVM;

import java.util.ArrayList;
import java.util.EnumSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhysPsychActivity extends AppCompatActivity {

    ArrayList<PhysicalItemVM> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phys_psych);
        ButterKnife.bind(this);
        arrayList = new ArrayList<>();

        RefreshRecycleView();
    }

    @OnClick(R.id.addActivity)
    void setAddActivity(){
        if (AreButtonsSets())
        {
            PhysicalItemVM item = new PhysicalItemVM(addSportName.getText().toString(),addSportValue.getText().toString(),addSportTime.getText().toString());
            arrayList.add(item);
            RefreshRecycleView();
            SetButtonsDefaultValue();
        }
    }

    void RefreshRecycleView(){
        RecyclerView.LayoutManager physicalLayoutManager = new LinearLayoutManager(getApplicationContext());
        physicalRecyclerView.setLayoutManager(physicalLayoutManager);
        PhysicalAdapter physicalAdapter = new PhysicalAdapter(arrayList);
        physicalRecyclerView.setAdapter(physicalAdapter);
    }
    void SetButtonsDefaultValue(){
        addSportName.setText("dodaj");
        addSportValue.setText("wybierz");
        addSportTime.setText("0");
    }
    boolean AreButtonsSets(){
        if (addSportName.getText()!="dodaj" &&
                addSportValue.getText() != "wybierz" &&
                addSportTime.getText() != 0+""){
            return true;
        }
        else {
            return false;
        }
    }

    //    region sports dialogs
    @OnClick(R.id.add_sport_name)
    void chooseSportName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PhysPsychActivity.this);
        builder.setTitle("Wybierz dzisiejszą aktywność");

        ArrayList<CheckboxesEnums.SportActivity> enumList = new ArrayList<CheckboxesEnums.SportActivity>(EnumSet.allOf(CheckboxesEnums.SportActivity.class));
        final String[] enumArray = new String[enumList.size()];
        int i = 0;
        for (CheckboxesEnums.SportActivity item : enumList){
            enumArray[i] = item.getName();
            i++;
        }

        builder.setItems(enumArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            addSportName.setText(enumArray[which]);
//                switch (which) {
//                    case 0: // horse
//                    case 1: // cow
//                    case 2: // camel
//                    case 3: // sheep
//                    case 4: // goat
//                }
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.add_sport_value)
    void chooseSportValue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PhysPsychActivity.this);
        builder.setTitle("Wybierz intensywność");

        ArrayList<CheckboxesEnums.SportValues> enumList = new ArrayList<CheckboxesEnums.SportValues>(EnumSet.allOf(CheckboxesEnums.SportValues.class));
        final String[] enumArray = new String[enumList.size()];
        int i = 0;
        for (CheckboxesEnums.SportValues item : enumList){
            enumArray[i] = item.getName();
            i++;
        }

        builder.setItems(enumArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addSportValue.setText(enumArray[which]);
//                switch (which) {
//                    case 0: // horse
//                    case 1: // cow
//                    case 2: // camel
//                    case 3: // sheep
//                    case 4: // goat
//                }
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.add_sport_time)
    void chooseSportTime(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PhysPsychActivity.this);
        builder.setTitle("Wybierz intensywność");

        ArrayList<CheckboxesEnums.SportTime> enumList = new ArrayList<CheckboxesEnums.SportTime>(EnumSet.allOf(CheckboxesEnums.SportTime.class));
        final String[] enumArray = new String[enumList.size()];
        int i = 0;
        for (CheckboxesEnums.SportTime item : enumList){
            enumArray[i] = item.getName();
            i++;
        }

        builder.setItems(enumArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addSportTime.setText(enumArray[which]);
//                switch (which) {
//                    case 0: // horse
//                    case 1: // cow
//                    case 2: // camel
//                    case 3: // sheep
//                    case 4: // goat
//                }
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
//    endregion


    @OnClick(R.id.next_button) void goNext(){
        Intent intent = new Intent(PhysPsychActivity.this,FruitsVegetables.class);
        startActivity(intent);
    }

    @BindView(R.id.addActivity)
    Button addActivity;
    @BindView(R.id.add_sport_name)
    Button addSportName;
    @BindView(R.id.add_sport_value)
    Button addSportValue;
    @BindView(R.id.add_sport_time)
    Button addSportTime;

    @BindView(R.id.physical_item_recycle_view)
    RecyclerView physicalRecyclerView;

    @BindView(R.id.next_button)
    Button nextButton;
}
