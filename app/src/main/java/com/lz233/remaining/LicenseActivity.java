package com.lz233.remaining;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.lz233.remaining.tools.File;

import java.io.IOException;
import java.io.InputStream;

public class LicenseActivity extends BaseActivity {
    private TextView calligraphy;
    private TextView flappyFrog;
    private TextView thisapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        //fb
        calligraphy = (TextView) findViewById(R.id.calligraphy);
        flappyFrog = (TextView) findViewById(R.id.flappyFrog);
        thisapp = (TextView) findViewById(R.id.thisapp);
        //许可证显示
        try {
            calligraphy.setText(File.readFromAssets(LicenseActivity.this,"Apache-License-2.0.txt"));
            flappyFrog.setText(File.readFromAssets(LicenseActivity.this,"Attribution-NonCommercial-4.0-International.txt"));
            thisapp.setText(File.readFromAssets(LicenseActivity.this,"Apache-License-2.0.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
