package com.xpf.leakcanarydemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.xpf.leakcanarydemo.R;

import java.lang.ref.WeakReference;

public class ActivityD extends AppCompatActivity {

    private TextView textView;

    /**
     * handler引起的内存泄漏：
     * 匿名内部类会隐式的持有外部类的引用，
     * 发一个10S的延迟消息，点击返回键，当Activity销毁时，由于消息还没有处理
     * 导致Activity无法回收，导致内存泄漏，即当Activity销毁时调用
     * removeCallbacksAndMessages移除所有消息和回调，可防止内存泄漏！
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("TAG", "收到了1...");
                    textView.setText("收到了1...");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
        textView = findViewById(R.id.textView);
        handler.sendEmptyMessageDelayed(1, 10000);

        //方式二： Post a message and 10S delay.
        //mHandler.postDelayed(sRunnable, 10000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
        handler.removeCallbacksAndMessages(null);// 移除所有消息和回调，防止内存泄漏！
    }

    ///////////////////////////////// 处理方式二 ///////////////////////////////////////////

    /**
     * 使用静态方式 + 弱引用
     * 当匿名内部类是static时，他们不会隐式的持有外部类的引用
     * 静态内部类和非静态内部类的区别很小，但我们必须都应该注意到这个差别，
     * 避免在Activity中使用非静态内部类，如果该类的实例会存在在Activity的生命周期之外时，
     * 我们必须使用静态内部类持有一个外部类的弱引用替代。
     */
    private static class MyHandler extends Handler {
        private final WeakReference<ActivityD> mActivity;

        public MyHandler(ActivityD activity) {
            mActivity = new WeakReference<ActivityD>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ActivityD activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        Log.e("TAG", "收到了1...");
                        break;
                }
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);

    private static final Runnable sRunnable = new Runnable() {
        @Override
        public void run() {

            // ...
        }
    };

}
