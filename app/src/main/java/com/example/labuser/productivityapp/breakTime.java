package com.example.labuser.productivityapp;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class breakTime extends AppCompatActivity {

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_time);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int workMin = bundle.getInt("work");
        int breakMin = bundle.getInt("break");

        final Intent toWorking = new Intent(this, working.class);
        Bundle workBundle = new Bundle();
        workBundle.putInt("work", workMin);
        workBundle.putInt("break", breakMin);
        toWorking.putExtras(workBundle);

        //Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alarm);

        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(toWorking);
                    }
                },
                workMin * 60 * 1000
        );
    }

    protected void cancelBtn(View v) {

        timer.cancel();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
