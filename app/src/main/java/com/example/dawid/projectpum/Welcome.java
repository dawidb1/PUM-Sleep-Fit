package com.example.dawid.projectpum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.R.id;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.layout;
import static com.example.dawid.projectpum.R.layout.activity_welcome;

public class Welcome extends AppCompatActivity {

    SharedPreferences prefs = null;
    String PATH = "results.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_welcome);
        bind(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        prefs = getSharedPreferences("com.example.dawid.projectpum", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit csvShared
            prefs.edit().putBoolean("firstrun", false).commit();

        } else {
            Intent intent = new Intent(Welcome.this, CommonBandSync.class);
            startActivity(intent);
        }
    }

    @OnClick(next_button)
    void goNext() {
        Intent intent = new Intent(Welcome.this, SetupSportActivities.class);
        startActivity(intent);
    }

    @BindView(next_button)
    FloatingActionButton nextButton;
}
