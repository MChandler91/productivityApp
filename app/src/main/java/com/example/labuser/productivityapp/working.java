package com.example.labuser.productivityapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class working extends AppCompatActivity {

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int workMin = bundle.getInt("work");
        int breakMin = bundle.getInt("break");

        TextView workTxt =(TextView)findViewById(R.id.txtWorkTime);
        workTxt.setText("Working for: " + workMin + " minute(s)");

        final Intent toBreakTime = new Intent(this, breakTime.class);
        Bundle breakBundle = new Bundle();
        breakBundle.putInt("work", workMin);
        breakBundle.putInt("break", breakMin);
        toBreakTime.putExtras(breakBundle);

        final AnimationDrawable hourglassAnimation;
        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alarm);

        ImageView anim = (ImageView) findViewById(R.id.hourGlassAnim);

        android.view.ViewGroup.LayoutParams layoutParams = anim.getLayoutParams();
        layoutParams.width = 400;
        layoutParams.height = 400;
        anim.setLayoutParams(layoutParams);

        anim.setBackgroundResource(R.drawable.animation);
        hourglassAnimation = (AnimationDrawable) anim.getBackground();
        hourglassAnimation.start();

         timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        r.play();
                        startActivity(toBreakTime);

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
