package com.example.dawid.projectpum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Welcome extends AppCompatActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        prefs = getSharedPreferences("com.example.dawid.projectpum", MODE_PRIVATE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }
        else {
            Intent intent = new Intent(Welcome.this,CommonBandSync.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.next_button) void goNext(){
        Intent intent = new Intent(Welcome.this,SetupSportActivities.class);
        startActivity(intent);
    }
    @BindView(R.id.next_button)
    Button nextButton;
}
