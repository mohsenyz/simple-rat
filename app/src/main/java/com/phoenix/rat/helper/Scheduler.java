package com.phoenix.rat.helper;

import android.util.TimeUtils;

import com.phoenix.rat.interfaces.Schedulable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by mphj on 7/17/17.
 */
public class Scheduler {
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static Schedulable schedulable;

    public static void init(Schedulable schedulable, long time, TimeUnit timeUnit){
        Scheduler.schedulable = schedulable;
        executorService.scheduleAtFixedRate(new SchedulerRunnable(), 0, time, timeUnit);
    }

    public static void destroy(){
        executorService.shutdownNow();
    }

    public static class SchedulerRunnable implements Runnable{
        @Override
        public void run(){
            Scheduler.schedulable.schedule();
        }
    }
}
