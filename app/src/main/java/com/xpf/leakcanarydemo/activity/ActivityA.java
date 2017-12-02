package com.xpf.leakcanarydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xpf.leakcanarydemo.R;
import com.xpf.leakcanarydemo.domain.TextViewUtil;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        TextView textView = findViewById(R.id.textView);
        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*
         * 单例模式的内存泄漏！
         *
         * 因为单例模式类的生命周期和整个应用的生命周期一样，
         * 当点击返回时，由于TextView被SingleTon单例类引用，
         * 所以虽然Activity销毁了，但是内存还是被占用着没有被释放，
         * 导致内存泄漏！
         */
        //SingleTon.getInstance().setTextView(textView);

        // 改写方式一：静态方法实现
        TextViewUtil.setText(textView);
        // 改写方式二：对象调方法

    }
}
