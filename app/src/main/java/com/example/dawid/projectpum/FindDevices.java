package com.example.dawid.projectpum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import static java.text.DateFormat.getTimeInstance;

/**
 * Created by Dawid on 19.04.2018.
 */

public class FindDevices extends AppCompatActivity {

    public static final String TAG = "StepCounter";
    int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 0533;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_devices);
        ButterKnife.bind(this);

        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_ACTIVITY_SEGMENT, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_ACTIVITY_SEGMENT, FitnessOptions.ACCESS_WRITE)
                .addDataType(DataType.TYPE_SPEED, FitnessOptions.ACCESS_WRITE)
                .build();
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);
        }
        else {
            accessGoogleFit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
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


        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_ACTIVITY_SEGMENT)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                if (dataSet.isEmpty()){
                                    Log.i(TAG,"dataset empty");
                                }
                                else {
                                    Value val = dataSet.getDataPoints().get(0).getValue(Field.FIELD_ACTIVITY);
                                    int result =  dataSet.getDataPoints().get(0).getValue(Field.FIELD_ACTIVITY).asInt();
                                    String s = val.asActivity();
                                    Log.i(TAG, "Activity: " + s + ".  Integer: " + result);
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
                                Log.w(TAG, "There was a problem getting the step count.", e);
                            }
                        });
    }
    private void readData() {
        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotal(DataType.TYPE_ACTIVITY_SEGMENT)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                if (dataSet.isEmpty()){
                                    Log.i(TAG,"dataset empty");
                                }
                                else {
                                    Value val = dataSet.getDataPoints().get(0).getValue(Field.FIELD_ACTIVITY);
                                    int result =  dataSet.getDataPoints().get(0).getValue(Field.FIELD_ACTIVITY).asInt();
                                    String s = val.asActivity();
                                    Log.i(TAG, "Activity: " + s + ".  Integer: " + result);
                                    List<DataPoint> listaAktywnosci = dataSet.getDataPoints();
                                    Log.i(TAG,"Ilość aktywności" + listaAktywnosci.size());
                                    for (DataPoint point:listaAktywnosci) {
                                        String x = point.getValue(Field.FIELD_ACTIVITY).asActivity();
                                        Log.i(TAG, "Activity: " + x);
                                    }
                                }
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
    private void readSession(){

    }

    private Task<SessionReadResponse> verifySession() {
        // Begin by creating the query.
        SessionReadRequest readRequest = readFitnessSession();

        // [START read_session]
        // Invoke the Sessions API to fetch the session with the query and wait for the result
        // of the read request. Note: Fitness.SessionsApi.readSession() requires the
        // ACCESS_FINE_LOCATION permission.
        return Fitness.getSessionsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readSession(readRequest)
                .addOnSuccessListener(new OnSuccessListener<SessionReadResponse>() {
                    @Override
                    public void onSuccess(SessionReadResponse sessionReadResponse) {
                        // Get a list of the sessions that match the criteria to check the result.
                        List<Session> sessions = sessionReadResponse.getSessions();
                        Log.i(TAG, "Session read was successful. Number of returned sessions is: "
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
                        Log.i(TAG, "Failed to read session");
                    }
                });
        // [END read_session]
    }

    private SessionReadRequest readFitnessSession() {
        Log.i(TAG, "Reading History API results for session: " + "SleepData?");
        // [START build_read_session_request]
        // Set a start and end time for our query, using a start time of 1 week before this moment.
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();

        // Build a session read request
        SessionReadRequest readRequest = new SessionReadRequest.Builder()
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .read(DataType.TYPE_ACTIVITY_SEGMENT)
                .setSessionName("Sleep session name")
                .build();
        // [END build_read_session_request]

        return readRequest;
    }

    private void dumpSession(Session session) {
        DateFormat dateFormat = getTimeInstance();
        Log.i(TAG, "Data returned for Session: " + session.getName()
                + "\n\tDescription: " + session.getDescription()
                + "\n\tStart: " + dateFormat.format(session.getStartTime(TimeUnit.MILLISECONDS))
                + "\n\tEnd: " + dateFormat.format(session.getEndTime(TimeUnit.MILLISECONDS)));
    }

    private void dumpDataSet(DataSet dataSet) {
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        for (DataPoint dp : dataSet.getDataPoints()) {
            DateFormat dateFormat = getTimeInstance();
            Log.i(TAG, "Data point:");
            Log.i(TAG, "\tType: " + dp.getDataType().getName());
            Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
            for(Field field : dp.getDataType().getFields()) {
                Log.i(TAG, "\tField: " + field.getName() +
                        " Value: " + dp.getValue(field));
            }
        }
    }

    @OnClick(R.id.refresh) void getData(){
        Log.i(TAG,"kliknieto");
        verifySession();
        readFitnessSession();
    }
    @OnClick(R.id.next_button) void goNext(){
        Intent intent = new Intent(FindDevices.this,SetupSportActivities.class);
        startActivity(intent);
    }
    @BindView(R.id.next_button)
    Button nextButton;
}
