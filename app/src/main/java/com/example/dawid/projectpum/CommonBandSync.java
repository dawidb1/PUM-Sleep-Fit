package com.example.dawid.projectpum;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dawid.projectpum.DAL.InstanceSaves.CsvModel;
import com.example.dawid.projectpum.DAL.InstanceSaves.ICsvHandler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.TimePickerDialog.OnTimeSetListener;
import static android.util.Log.i;
import static android.util.Log.w;
import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.DAL.Helpers.EasyConverter.TimeToString;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.id.pickEndTime;
import static com.example.dawid.projectpum.R.id.pickStartTime;
//import static com.example.dawid.projectpum.R.id.setup_button;
import static com.example.dawid.projectpum.R.id.setup_button;
import static com.example.dawid.projectpum.R.id.step_count;
import static com.example.dawid.projectpum.R.id.sync_button;
import static com.example.dawid.projectpum.R.layout.activity_common_band_sync;
import static com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount;
import static com.google.android.gms.auth.api.signin.GoogleSignIn.hasPermissions;
import static com.google.android.gms.fitness.Fitness.getHistoryClient;
import static com.google.android.gms.fitness.FitnessOptions.ACCESS_READ;
import static com.google.android.gms.fitness.FitnessOptions.builder;
import static com.google.android.gms.fitness.data.DataType.AGGREGATE_STEP_COUNT_DELTA;
import static com.google.android.gms.fitness.data.DataType.TYPE_STEP_COUNT_DELTA;
import static com.google.android.gms.fitness.data.Field.FIELD_STEPS;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.getInstance;

public class CommonBandSync extends AppCompatActivity implements ICsvHandler {

    private int mHour, mMinute;
    public static final String TAG = "StepCounter";
    int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 0533;
    Context context = null;
    public CsvModel model = null;
    public SharedPreferences shared = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_common_band_sync);
        bind(this);
        context = this;
        model = new CsvModel();
        shared = getSharedPreferences("csv_model", MODE_PRIVATE);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view = getSupportActionBar().getCustomView();

        FitnessOptions fitnessOptions = builder()
                .addDataType(TYPE_STEP_COUNT_DELTA, ACCESS_READ)
                .addDataType(AGGREGATE_STEP_COUNT_DELTA, ACCESS_READ)
                .build();
        if (!hasPermissions(getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    getLastSignedInAccount(this),
                    fitnessOptions);
        } else {
            readData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        fillModel();
        saveSharedPrefFromModel();
        saveStateFromModel(outState);
        super.onSaveInstanceState(outState);
    }
    public void fillModel(){
        model.StartSleepString =  startTime.getText().toString();
        model.EndSleepString = endTime.getText().toString();
        model.Steps = Integer.parseInt(stepCount.getText().toString());
    }
    public void saveStateFromModel(Bundle outState){
        outState.putString("startSleep",model.StartSleepString);
        outState.putString("endSleep", model.EndSleepString);
        outState.putInt("steps", model.Steps);
    }
    public void saveSharedPrefFromModel(){
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("startSleep", model.StartSleepString);
        editor.putString("endSleep", model.EndSleepString);
        editor.putString("stepsString", model.Steps+"");
        editor.putInt("steps", model.Steps);
        editor.apply();

        int x = shared.getInt("steps", 0);
        Log.i("steps: ",x +"");
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        startTime.setText(savedInstanceState.get("startSleep").toString());
        endTime.setText(savedInstanceState.get("endSleep").toString());
    }

    private void readData() {
        stepCount.setText("...");
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Synchronizowanie danych");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();


        getHistoryClient(this, getLastSignedInAccount(this))
                .readDailyTotal(TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                long total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(FIELD_STEPS).asInt();

                                stepCount.setText(total + "");
                                i(TAG, "Total steps: " + total);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                    }
                                }, 1000); // 3000 milliseconds delay
                                syncButton.setBackgroundColor(ContextCompat.getColor(context,R.color.primaryLightColor));
//                                okText.setVisibility(VISIBLE);
                                syncButton.setText("Zsynchronizowano");
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                w(TAG, "There was a problem getting the step count.", e);
                            }
                        });
    }

    @OnClick(pickStartTime)
    void setStartTime() {
        TimePickerSupport(startTime);
    }

    @OnClick(pickEndTime)
    void setEndTime() {
        TimePickerSupport(endTime);
    }

    void TimePickerSupport(final TextView textView) {
        final Calendar c = getInstance();
        mHour = c.get(HOUR_OF_DAY);
        mMinute = c.get(MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        textView.setText(TimeToString(hourOfDay, minute));
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @OnClick(next_button)
    void goNext() {
        String x = startTime.getText().toString();
        if (!startTime.getText().equals("Ustaw") && !endTime.getText().equals("Ustaw")){
            Intent intent = new Intent(CommonBandSync.this, DayInfo.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"Uzupełnij czasy snu!",Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(setup_button)
    void firstUseSetup() {
        Intent intent = new Intent(CommonBandSync.this, SetupSportActivities.class);
        startActivity(intent);
    }

    @OnClick(sync_button)
    void syncSteps() {
        readData();
    }

    @BindView(step_count)
    TextView stepCount;

    @BindView(sync_button)
    Button syncButton;


    @BindView(next_button)
    FloatingActionButton nextButton;

    @BindView(pickStartTime)
    Button startTime;

    @BindView(pickEndTime)
    Button endTime;
}
