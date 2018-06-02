package com.example.dawid.projectpum;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dawid.projectpum.DAL.Adapters.PhysicalAdapter;
import com.example.dawid.projectpum.DAL.CheckboxesEnums;
import com.example.dawid.projectpum.DAL.PhysicalItemVM;

import java.util.ArrayList;
import java.util.EnumSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.DialogInterface.OnClickListener;
import static android.support.v7.app.AlertDialog.Builder;
import static android.support.v7.widget.RecyclerView.LayoutManager;
import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.DAL.CheckboxesEnums.SportActivity;
import static com.example.dawid.projectpum.DAL.CheckboxesEnums.SportTime;
import static com.example.dawid.projectpum.DAL.CheckboxesEnums.SportValues;
import static com.example.dawid.projectpum.R.id;
import static com.example.dawid.projectpum.R.id.add_sport_name;
import static com.example.dawid.projectpum.R.id.add_sport_time;
import static com.example.dawid.projectpum.R.id.add_sport_value;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.id.physical_item_recycle_view;
import static com.example.dawid.projectpum.R.layout;
import static com.example.dawid.projectpum.R.layout.activity_phys_psych;
import static java.util.EnumSet.allOf;

public class PhysPsychActivity extends AppCompatActivity {

    ArrayList<PhysicalItemVM> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_phys_psych);
        bind(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        arrayList = new ArrayList<>();
        RefreshRecycleView();
        SetButtonsDefaultValue();
    }

    @OnClick(id.addActivity)
    void setAddActivity() {
        if (AreButtonsSets()) {
            PhysicalItemVM item = new PhysicalItemVM(addSportName.getText().toString(), addSportValue.getText().toString(), addSportTime.getText().toString());
            arrayList.add(item);
            RefreshRecycleView();
            SetButtonsDefaultValue();
        }
        else {
            Toast.makeText(this,"Uzupełnij aktywność!",Toast.LENGTH_LONG).show();
        }
    }

    void RefreshRecycleView() {
        LayoutManager physicalLayoutManager = new LinearLayoutManager(getApplicationContext());
        physicalRecyclerView.setLayoutManager(physicalLayoutManager);
        PhysicalAdapter physicalAdapter = new PhysicalAdapter(arrayList);
        physicalRecyclerView.setAdapter(physicalAdapter);
    }

    void SetButtonsDefaultValue() {
        addSportName.setText("dodaj");
        addSportValue.setText("wybierz");
        addSportTime.setText("0");
    }

    boolean AreButtonsSets() {
        if (addSportName.getText() != "dodaj" &&
                addSportValue.getText() != "wybierz" &&
                addSportTime.getText() != 0 + "") {
            return true;
        } else {
            return false;
        }
    }

    //    region sports dialogs
    @OnClick(add_sport_name)
    void chooseSportName() {
        Builder builder = new Builder(PhysPsychActivity.this);
        builder.setTitle("Wybierz dzisiejszą aktywność");

        ArrayList<SportActivity> enumList = new ArrayList<SportActivity>(allOf(SportActivity.class));
        final String[] enumArray = new String[enumList.size()];
        int i = 0;
        for (SportActivity item : enumList) {
            enumArray[i] = item.getName();
            i++;
        }

        builder.setItems(enumArray, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addSportName.setText(enumArray[which]);
                chooseSportValue();
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

    @OnClick(add_sport_value)
    void chooseSportValue() {
        Builder builder = new Builder(PhysPsychActivity.this);
        builder.setTitle("Wybierz intensywność");

        ArrayList<SportValues> enumList = new ArrayList<SportValues>(allOf(SportValues.class));
        final String[] enumArray = new String[enumList.size()];
        int i = 0;
        for (SportValues item : enumList) {
            enumArray[i] = item.getName();
            i++;
        }

        builder.setItems(enumArray, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addSportValue.setText(enumArray[which]);
                chooseSportTime();
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

    @OnClick(add_sport_time)
    void chooseSportTime() {
        Builder builder = new Builder(PhysPsychActivity.this);
        builder.setTitle("Wybierz intensywność");

        ArrayList<SportTime> enumList = new ArrayList<SportTime>(allOf(SportTime.class));
        final String[] enumArray = new String[enumList.size()];
        int i = 0;
        for (SportTime item : enumList) {
            enumArray[i] = item.getName();
            i++;
        }

        builder.setItems(enumArray, new OnClickListener() {
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


    @OnClick(next_button)
    void goNext() {
        Intent intent = new Intent(PhysPsychActivity.this, FruitsVegetables.class);
        startActivity(intent);
    }

    @BindView(id.addActivity)
    FloatingActionButton addActivity;
    @BindView(add_sport_name)
    Button addSportName;
    @BindView(add_sport_value)
    Button addSportValue;
    @BindView(add_sport_time)
    Button addSportTime;

    @BindView(physical_item_recycle_view)
    RecyclerView physicalRecyclerView;

    @BindView(next_button)
    FloatingActionButton nextButton;
}
