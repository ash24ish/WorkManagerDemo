package com.ashishbharam.workmanagerdemo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;

public class RandomNumberGenerator extends Worker {

    private int mRandomNumber;
    private boolean isRandomNumberGeneratorOn;
    Context context;
    WorkerParameters workerParameters;

    public RandomNumberGenerator(@NonNull @org.jetbrains.annotations.NotNull Context context, @NonNull @org.jetbrains.annotations.NotNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.workerParameters = workerParams;
        isRandomNumberGeneratorOn = true;
    }

    private void startRandomNumberGenerator(){
        int i = 0;
        while (i<5 && !isStopped()){
            try{
                Thread.sleep(1000);
                if (isRandomNumberGeneratorOn){
                    mRandomNumber = new Random().nextInt(999)+99;
                    Log.i("TAG", "Thread: "+Thread.currentThread().getId()+" Random Number: "+mRandomNumber);
                    i++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.i("TAG", "Thread interrupted ");
            }
        }
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i("TAG", "Worker1 has been cancelled ");
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Result doWork() {
        startRandomNumberGenerator();
        return Result.success();
    }
}
