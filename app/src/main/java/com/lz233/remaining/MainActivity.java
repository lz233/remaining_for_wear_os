package com.lz233.remaining;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends BaseActivity {
    private TextView sectextview;
    private LinearLayout linearLayout;
    private TextView firsttips_textview;
    private TextView realtime_textview;
    private SharedPreferences sharedPreferences;
    private SoundPool soundPool;
    protected HashMap<String, Integer> soundMap = new HashMap<String, Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fb
        sectextview = (TextView) findViewById(R.id.sec_textview);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        firsttips_textview = (TextView) findViewById(R.id.firsttips_textview);
        realtime_textview = (TextView) findViewById(R.id.realtime_textview);
        //设置字体
        //sectextview.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Googlesans-Bold.ttf"));
        //realtime_textview.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Googlesans-Bold.ttf"));
        // Enables Always-on
        //setAmbientEnabled();
        //sharedPreferences
        sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        //第一次的提示显示
        Boolean fiset_open = sharedPreferences.getBoolean("first_open",true);
        if (fiset_open){
            firsttips_textview.setVisibility(View.VISIBLE);
            editor.putBoolean("first_open",false);
            editor.commit();
            //延迟消失
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    firsttips_textview.setVisibility(View.GONE);
                }
            },3500);
        }
        //调用显示真实时间
        gettime();
        //textview初始化
        int sec = sharedPreferences.getInt("sec", 0);
        sectextview.setText("+"+String.valueOf(sec)+"s");
        //soundpool初始化
        init();
        final Random random = new Random();
        //设置监听器
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sec=(sharedPreferences.getInt("sec",0))+1;
                sectextview.setText("+"+String.valueOf(sec)+"s");
                editor.putInt("sec",sec);
                editor.commit();
                //随机数生成
                int ran = 1;
                while (true) {
                    ran = random.nextInt(64);
                    if (ran != 0){
                        break;
                    }
                }
                soundPool.play(soundMap.get("s"+String.valueOf(ran)), 1, 1, 0, 0, 1);
            }
        });
        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent().setClass(MainActivity.this, AboutActivity.class));
                return false;
            }
        });
    }
    //soundpool初始化
    private void init() {
        try {
            String[] files = this.getResources().getAssets().list("sounds");
            //soundPool=new SoundPool(files.length, AudioManager.STREAM_MUSIC, 0);
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(1);
            AudioAttributes.Builder attrBuild = new AudioAttributes.Builder();
            attrBuild.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuild.build());
            soundPool = builder.build();
            for (int i = 0; i < files.length; i++) {
                if (files[i].contains(".mp3")) {
                    String filename = files[i].substring(0, files[i].indexOf("."));//获取文件名称
                    soundMap.put(filename, soundPool.load(getAssets().openFd("sounds/" + files[i]), 1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //显示系统时间的进程
    private void gettime () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                displayrealtime();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gettime();
            }
        }).start();
    }
    //显示真实时间
    private void displayrealtime () {
        Calendar calendar = Calendar.getInstance();
        int sec = (sharedPreferences.getInt("sec",0));
        calendar.add(Calendar.SECOND,sec);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        final int second = calendar.get(Calendar.SECOND);
        realtime_textview.post(new Runnable(){
            @Override
            public void run() {
                realtime_textview.setText(getApplicationContext().getString(R.string.your_real_time)+String.valueOf(year)+"."+String.valueOf(month)+"."+String.valueOf(day)+" "+String.valueOf(hour)+":"+String.valueOf(minute)+":"+String.valueOf(second));
            }
        });
    }
}
