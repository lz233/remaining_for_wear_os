package com.lz233.remaining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lz233.remaining.tools.App;

public class AboutActivity extends BaseActivity {
    private TextView appver;
    private TextView about_licenses;
    private TextView about_developer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //fb
        appver = (TextView) findViewById(R.id.appver);
        about_licenses = (TextView) findViewById(R.id.about_licenses);
        about_developer = (TextView) findViewById(R.id.about_developer);
        //textview
        appver.setText(App.getAppVersionName(AboutActivity.this)+" ("+String.valueOf(App.getAppVersionCode(AboutActivity.this)+")"));
        //设置监听器
        about_licenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(AboutActivity.this, LicenseActivity.class));
            }
        });
        about_developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(AboutActivity.this, AboutDeveloperActivity.class));
            }
        });
    }
}
