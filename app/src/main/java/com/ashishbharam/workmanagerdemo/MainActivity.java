package com.ashishbharam.workmanagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button btnStartMyService, btnStopMyService;
    WorkManager workManager;
    WorkRequest workRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("TAG", "MainActivity thread ID: " + Thread.currentThread().getId());

        btnStartMyService = findViewById(R.id.btnStartService);
        btnStopMyService = findViewById(R.id.btnStopService);

        workManager = WorkManager.getInstance(getApplicationContext());
        workRequest = new PeriodicWorkRequest.Builder(RandomNumberGenerator.class,15, TimeUnit.MINUTES).build();

        btnStartMyService.setOnClickListener(v -> {
            Log.i("TAG", "Work Request Started: ");
            workManager.enqueue(workRequest);
            Toast.makeText(this, "Work Request Started", Toast.LENGTH_SHORT).show();
        });

        btnStopMyService.setOnClickListener(v -> {
            Log.i("TAG", "Work Request Stopped by user. ");
            workManager.cancelWorkById(workRequest.getId());
            Toast.makeText(this, "Work Request Stopped", Toast.LENGTH_SHORT).show();
        });
    }
}