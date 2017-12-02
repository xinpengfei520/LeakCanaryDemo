package com.xpf.leakcanarydemo.domain;

import android.widget.TextView;

public class SingleTon {

    private static SingleTon instance;
    private TextView mTextView;

    public static SingleTon getInstance() {
        if (instance == null) {
            instance = new SingleTon();
        }
        return instance;
    }

    public void setTextView(TextView textView) {
        mTextView = textView;
        textView.setText("SingleTon");
    }
}