package com.xpf.leakcanarydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xpf.leakcanarydemo.activity.ActivityA;
import com.xpf.leakcanarydemo.activity.ActivityB;
import com.xpf.leakcanarydemo.activity.ActivityC;
import com.xpf.leakcanarydemo.activity.ActivityD;
import com.xpf.leakcanarydemo.activity.ActivityE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTest1 = findViewById(R.id.btnTest1);
        Button btnTest2 = findViewById(R.id.btnTest2);
        Button btnTest3 = findViewById(R.id.btnTest3);
        Button btnTest4 = findViewById(R.id.btnTest4);
        Button btnTest5 = findViewById(R.id.btnTest5);
        btnTest1.setOnClickListener(this);
        btnTest2.setOnClickListener(this);
        btnTest3.setOnClickListener(this);
        btnTest4.setOnClickListener(this);
        btnTest5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTest1:
                startActivity(new Intent(MainActivity.this, ActivityA.class));
                break;
            case R.id.btnTest2:
                startActivity(new Intent(MainActivity.this, ActivityB.class));
                break;
            case R.id.btnTest3:
                startActivity(new Intent(MainActivity.this, ActivityC.class));
                break;
            case R.id.btnTest4:
                startActivity(new Intent(MainActivity.this, ActivityD.class));
                break;
            case R.id.btnTest5:
                startActivity(new Intent(MainActivity.this, ActivityE.class));
                break;
        }
    }
}
