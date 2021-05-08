package com.ashishbharam.workmanagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button btnStartMyService, btnStopMyService;
    WorkManager workManager;
    WorkRequest workRequest, workRequest1, workRequest2, workRequest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("TAG", "MainActivity thread ID: " + Thread.currentThread().getId());

        btnStartMyService = findViewById(R.id.btnStartService);
        btnStopMyService = findViewById(R.id.btnStopService);

        workManager = WorkManager.getInstance(getApplicationContext());
        //workRequest = new PeriodicWorkRequest.Builder(RandomNumberGenerator.class,15, TimeUnit.MINUTES).build();
        workRequest1 = new OneTimeWorkRequest.Builder(RandomNumberGenerator.class).addTag("worker1").build();
        workRequest2 = new OneTimeWorkRequest.Builder(RandomNumberGenerator2.class).addTag("worker2").build();
        workRequest3 = new OneTimeWorkRequest.Builder(RandomNumberGenerator3.class).addTag("worker3").build();
        //according to documentation we can not do periodic work request chaining

        btnStartMyService.setOnClickListener(v -> {
            Log.i("TAG", "Work Request Started: ");
            //workManager.enqueue(workRequest);
            //workManager.beginWith((OneTimeWorkRequest) workRequest1).then((OneTimeWorkRequest) workRequest2).then((OneTimeWorkRequest) workRequest3).enqueue();
            workManager.beginWith(Arrays.asList((OneTimeWorkRequest) workRequest1, (OneTimeWorkRequest) workRequest2)).then((OneTimeWorkRequest) workRequest3).enqueue();
            Toast.makeText(this, "Work Request Started", Toast.LENGTH_SHORT).show();
        });

        btnStopMyService.setOnClickListener(v -> {
            Log.i("TAG", "Work Request Stopped by user. ");
            //workManager.cancelWorkById(workRequest.getId());
            workManager.cancelAllWorkByTag("worker3");
            Toast.makeText(this, "Work Request Stopped", Toast.LENGTH_SHORT).show();
        });
    }
}