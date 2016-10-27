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


        TextView breakTxt =(TextView)findViewById(R.id.txtBreakTime);
        breakTxt.setText("Take a break for: " + breakMin + " minute(s)");

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
                        startActivity(toWorking);
                    }
                },
                breakMin * 60 * 1000
        );
    }

    protected void cancelBtn(View v) {

        timer.cancel();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
