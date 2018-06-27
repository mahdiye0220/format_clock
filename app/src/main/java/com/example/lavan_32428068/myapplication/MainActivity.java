package com.example.lavan_32428068.myapplication;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    int h, m;
    int i = 0;
    private TextView hours, minutes;
    private List<Integer> sounds = new ArrayList<>();
    private int loc = 0;
    int[] sounds1 = {
            0, R.raw.s1, R.raw.s2, R.raw.s3, R.raw.s4, R.raw.s5,
            R.raw.s6, R.raw.s7, R.raw.s8, R.raw.s9, R.raw.s10,
            R.raw.s11, R.raw.s12, R.raw.s13, R.raw.s14, R.raw.s15,
            R.raw.s16, R.raw.s17, R.raw.s18, R.raw.s19, R.raw.s20,
    };
    int[] sounds1o = {
            0, R.raw.s1o, R.raw.s2o, R.raw.s3o, R.raw.s4o, R.raw.s5o,
            R.raw.s6o, R.raw.s7o, R.raw.s8o, R.raw.s9o, R.raw.s10o,
            R.raw.s11o, R.raw.s12o, R.raw.s13o, R.raw.s14o, R.raw.s15o,
            R.raw.s16o, R.raw.s17o, R.raw.s18o, R.raw.s19o, R.raw.s20o,
    };
    int[] sounds10o = {
            0, R.raw.s10o, R.raw.s20o, R.raw.s30o, R.raw.s40o, R.raw.s50o,
    };
    int[] sounds10 = {
            0, R.raw.s10, R.raw.s20, R.raw.s30, R.raw.s40, R.raw.s50,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hours = (TextView) findViewById(R.id.hours);
        minutes = (TextView) findViewById(R.id.minutes);
        Button say = (Button) findViewById(R.id.say);
        say.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    return;
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.saat);
                loc = 0;
                mediaPlayer.setOnCompletionListener(MainActivity.this);
                mediaPlayer.start();
            }
        });

        Typeface t = Typeface.createFromAsset(getAssets(), "digital7.ttf");
        hours.setTypeface(t);
        minutes.setTypeface(t);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sounds = new ArrayList<>();
                switch (checkedId) {
                    case R.id.radio24h:
                        sayTime(false);
                        break;
                    case R.id.radio12h:
                        sayTime(true);
                        break;
                }
                sounds.add(0);
            }
        });
    }

    public void sayTime(boolean is12) {
        Date d = new Date();
        h = d.getHours();
        m = d.getMinutes();

        if (is12 && h > 12) {
            h -= 12;
        }
        String hs = String.format("%02d", h);
        String ms = String.format("%02d", m);
        hours.setText(hs);
        minutes.setText(ms);
        if (m == 0) {
            sounds.add(sounds1[h]);
        } else {
            if (h < 20) {
                sounds.add(sounds1o[h]);
            } else {
                int h10 = h / 10;
                int h1 = h % 10;
                sounds.add(h1 == 0 ? sounds10[h10] : sounds10o[h10]);
                if (h1 != 0)
                    sounds.add(sounds1[h1]);
            }
            if (m < 20)
                sounds.add(sounds1[m]);
            else {
                int m10 = m / 10;
                int m1 = m % 10;
                sounds.add(m1 == 0 ? sounds10[m10] : sounds10o[m10]);
                if (m1 != 0)
                    sounds.add(sounds1[m1]);
            }
            sounds.add(R.raw.daghigheh);
        }
        final SharedPreferences sp = getSharedPreferences("mysaat", MODE_PRIVATE);
        sp.edit().putInt("mysaat", h).apply();
    }


    MediaPlayer mediaPlayer;
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (loc < sounds.size() && sounds.get(loc) != 0) {
            mediaPlayer= MediaPlayer.create(this, sounds.get(loc));
            loc++;
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.start();
        }
    }
 }

