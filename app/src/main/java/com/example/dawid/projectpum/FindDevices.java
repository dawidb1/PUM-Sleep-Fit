package com.example.dawid.projectpum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.fitness.result.SessionReadResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.util.Log.i;
import static android.util.Log.w;
import static butterknife.ButterKnife.bind;
import static com.example.dawid.projectpum.R.id;
import static com.example.dawid.projectpum.R.id.next_button;
import static com.example.dawid.projectpum.R.id.refresh;
import static com.example.dawid.projectpum.R.layout;
import static com.example.dawid.projectpum.R.layout.activity_find_devices;
import static com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount;
import static com.google.android.gms.auth.api.signin.GoogleSignIn.hasPermissions;
import static com.google.android.gms.fitness.Fitness.getHistoryClient;
import static com.google.android.gms.fitness.Fitness.getSessionsClient;
import static com.google.android.gms.fitness.FitnessOptions.ACCESS_READ;
import static com.google.android.gms.fitness.FitnessOptions.ACCESS_WRITE;
import static com.google.android.gms.fitness.FitnessOptions.builder;
import static com.google.android.gms.fitness.data.DataType.AGGREGATE_STEP_COUNT_DELTA;
import static com.google.android.gms.fitness.data.DataType.TYPE_ACTIVITY_SEGMENT;
import static com.google.android.gms.fitness.data.DataType.TYPE_SPEED;
import static com.google.android.gms.fitness.data.DataType.TYPE_STEP_COUNT_DELTA;
import static com.google.android.gms.fitness.data.Field.FIELD_ACTIVITY;
import static com.google.android.gms.fitness.request.SessionReadRequest.Builder;
import static java.text.DateFormat.getTimeInstance;
import static java.util.Calendar.WEEK_OF_YEAR;
import static java.util.Calendar.getInstance;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Dawid on 19.04.2018.
 */

public class FindDevices extends AppCompatActivity {

    public static final String TAG = "StepCounter";
    int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 0533;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_find_devices);
        bind(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        FitnessOptions fitnessOptions = builder()
                .addDataType(TYPE_STEP_COUNT_DELTA, ACCESS_READ)
                .addDataType(AGGREGATE_STEP_COUNT_DELTA, ACCESS_READ)
                .addDataType(TYPE_ACTIVITY_SEGMENT, ACCESS_READ)
                .addDataType(TYPE_ACTIVITY_SEGMENT, ACCESS_WRITE)
                .addDataType(TYPE_SPEED, ACCESS_WRITE)
                .build();
        if (!hasPermissions(getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    getLastSignedInAccount(this),
                    fitnessOptions);
        } else {
            accessGoogleFit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
//                accessGoogleFit();
            }
        }
    }

    private void accessGoogleFit() {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        long endTime = cal.getTimeInMillis();
//        cal.add(Calendar.YEAR, -1);
//        long startTime = cal.getTimeInMillis();
//
//        DataReadRequest readRequest = new DataReadRequest.Builder()
//                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
//                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
//                .build();


        getHistoryClient(this, getLastSignedInAccount(this))
                .readDailyTotal(TYPE_ACTIVITY_SEGMENT)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                if (dataSet.isEmpty()) {
                                    i(TAG, "dataset empty");
                                } else {
                                    Value val = dataSet.getDataPoints().get(0).getValue(FIELD_ACTIVITY);
                                    int result = dataSet.getDataPoints().get(0).getValue(FIELD_ACTIVITY).asInt();
                                    String s = val.asActivity();
                                    i(TAG, "Activity: " + s + ".  Integer: " + result);
                                }
//                                int total =
//                                        dataSet.isEmpty()
//                                                ? '0'
//                                                : dataSet.getDataPoints().get(0).(Field.FIELD_ACTIVITY_CONFIDENCE.getName().toString());
//                                Log.i(TAG, "Total steps: " + total);
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

    private void readData() {
        getHistoryClient(this, getLastSignedInAccount(this))
                .readDailyTotal(TYPE_ACTIVITY_SEGMENT)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                if (dataSet.isEmpty()) {
                                    i(TAG, "dataset empty");
                                } else {
                                    Value val = dataSet.getDataPoints().get(0).getValue(FIELD_ACTIVITY);
                                    int result = dataSet.getDataPoints().get(0).getValue(FIELD_ACTIVITY).asInt();
                                    String s = val.asActivity();
                                    i(TAG, "Activity: " + s + ".  Integer: " + result);
                                    List<DataPoint> listaAktywnosci = dataSet.getDataPoints();
                                    i(TAG, "Ilość aktywności" + listaAktywnosci.size());
                                    for (DataPoint point : listaAktywnosci) {
                                        String x = point.getValue(FIELD_ACTIVITY).asActivity();
                                        i(TAG, "Activity: " + x);
                                    }
                                }
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

    private void readSession() {

    }

    private Task<SessionReadResponse> verifySession() {
        // Begin by creating the query.
        SessionReadRequest readRequest = readFitnessSession();

        // [START read_session]
        // Invoke the Sessions API to fetch the session with the query and wait for the result
        // of the read request. Note: Fitness.SessionsApi.readSession() requires the
        // ACCESS_FINE_LOCATION permission.
        return getSessionsClient(this, getLastSignedInAccount(this))
                .readSession(readRequest)
                .addOnSuccessListener(new OnSuccessListener<SessionReadResponse>() {
                    @Override
                    public void onSuccess(SessionReadResponse sessionReadResponse) {
                        // Get a list of the sessions that match the criteria to check the result.
                        List<Session> sessions = sessionReadResponse.getSessions();
                        i(TAG, "Session read was successful. Number of returned sessions is: "
                                + sessions.size());

                        for (Session session : sessions) {
                            // Process the session
                            dumpSession(session);

                            // Process the data sets for this session
                            List<DataSet> dataSets = sessionReadResponse.getDataSet(session);
                            for (DataSet dataSet : dataSets) {
                                dumpDataSet(dataSet);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        i(TAG, "Failed to read session");
                    }
                });
        // [END read_session]
    }

    private SessionReadRequest readFitnessSession() {
        i(TAG, "Reading History API results for session: " + "SleepData?");
        // [START build_read_session_request]
        // Set a start and end time for our query, using a start time of 1 week before this moment.
        Calendar cal = getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();

        // Build a session read request
        SessionReadRequest readRequest = new Builder()
                .setTimeInterval(startTime, endTime, MILLISECONDS)
                .read(TYPE_ACTIVITY_SEGMENT)
                .setSessionName("Sleep session name")
                .build();
        // [END build_read_session_request]

        return readRequest;
    }

    private void dumpSession(Session session) {
        DateFormat dateFormat = getTimeInstance();
        i(TAG, "Data returned for Session: " + session.getName()
                + "\n\tDescription: " + session.getDescription()
                + "\n\tStart: " + dateFormat.format(session.getStartTime(MILLISECONDS))
                + "\n\tEnd: " + dateFormat.format(session.getEndTime(MILLISECONDS)));
    }

    private void dumpDataSet(DataSet dataSet) {
        i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        for (DataPoint dp : dataSet.getDataPoints()) {
            DateFormat dateFormat = getTimeInstance();
            i(TAG, "Data point:");
            i(TAG, "\tType: " + dp.getDataType().getName());
            i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(MILLISECONDS)));
            i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(MILLISECONDS)));
            for (Field field : dp.getDataType().getFields()) {
                i(TAG, "\tField: " + field.getName() +
                        " Value: " + dp.getValue(field));
            }
        }
    }

    @OnClick(refresh)
    void getData() {
        i(TAG, "kliknieto");
        verifySession();
        readFitnessSession();
    }

    @OnClick(next_button)
    void goNext() {
        Intent intent = new Intent(FindDevices.this, SetupSportActivities.class);
        startActivity(intent);
    }

    @BindView(next_button)
    FloatingActionButton nextButton;
}
