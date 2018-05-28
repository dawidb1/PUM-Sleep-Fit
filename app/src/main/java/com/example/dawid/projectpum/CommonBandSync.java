package com.example.dawid.projectpum;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dawid.projectpum.DAL.Helpers.TimeFormatter;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonBandSync extends AppCompatActivity {

    private int mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_band_sync);
        ButterKnife.bind(this);
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

    @BindView(R.id.next_button)
    Button nextButton;

    @BindView(R.id.pickStartTime)
    Button startTime;

    @BindView(R.id.pickEndTime)
    Button endTime;
//
//    @BindView(R.id.start_time_text)
//    TextView startTime;
//
//    @BindView(R.id.end_time_text)
//    TextView endTime;

}
