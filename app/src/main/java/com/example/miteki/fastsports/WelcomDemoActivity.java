package com.example.miteki.fastsports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_demo);
    }

    public void startSearchActivity(final View view) {
        Intent intent = new Intent(WelcomDemoActivity.this, SearchSportsActivity.class);
        startActivity(intent);
    }

    public void startSupportActivity(final View view) {
        Intent intent = new Intent(WelcomDemoActivity.this, DemoNoticeActivity.class);
        startActivity(intent);
    }
}
