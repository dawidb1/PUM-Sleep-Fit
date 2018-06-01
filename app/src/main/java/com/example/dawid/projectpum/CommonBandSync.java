package com.example.dawid.projectpum;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dawid.projectpum.DAL.Helpers.TimeFormatter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonBandSync extends AppCompatActivity {

    private int mHour, mMinute;
    public static final String TAG = "StepCounter";
    int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 0533;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_band_sync);
        ButterKnife.bind(this);

        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);
        }
        else {
            readData();
        }
    }

    private void readData() {
        final ProgressDialog dialog=new ProgressDialog(this);
            dialog.setMessage("Loading step data");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                long total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();

                                stepCount.setText(total+"");
                                Log.i(TAG, "Total steps: " + total);
                                dialog.hide();
                                syncButton.setBackgroundColor(Color.GREEN);
                                okText.setVisibility(View.VISIBLE);
                                syncButton.setText("Zsynchronizowano");
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "There was a problem getting the step count.", e);
                            }
                        });
    }

    @OnClick(R.id.pickStartTime)
    void setStartTime(){
        TimePickerSupport(startTime);
    }

    @OnClick(R.id.pickEndTime)
    void setEndTime(){
        TimePickerSupport(endTime);
    }
    void TimePickerSupport(final TextView textView){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        textView.setText(TimeFormatter.TimeToString(hourOfDay,minute));
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @OnClick(R.id.next_button) void goNext(){
        Intent intent = new Intent(CommonBandSync.this,DayInfo.class);
        startActivity(intent);
    }
    @OnClick(R.id.setup) void firstUseSetup(){
        Intent intent = new Intent(CommonBandSync.this,SetupSportActivities.class);
        startActivity(intent);
    }

    @OnClick(R.id.sync_button) void syncSteps(){
        readData();
    }

    @BindView(R.id.ok_button)
    TextView okText;

    @BindView(R.id.step_count)
    TextView stepCount;

    @BindView(R.id.sync_button)
    Button syncButton;

    @BindView(R.id.next_button)
    Button nextButton;

    @BindView(R.id.pickStartTime)
    Button startTime;

    @BindView(R.id.pickEndTime)
    Button endTime;
}
