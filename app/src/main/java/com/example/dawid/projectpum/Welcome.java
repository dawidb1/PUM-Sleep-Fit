package com.example.dawid.projectpum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.next_button) void goNext(){
        Intent intent = new Intent(Welcome.this,FindDevices.class);
        startActivity(intent);
    }
    @BindView(R.id.next_button)
    Button nextButton;
}
