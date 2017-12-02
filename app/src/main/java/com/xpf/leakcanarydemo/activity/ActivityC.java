package com.xpf.leakcanarydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;

import com.xpf.leakcanarydemo.R;

public class ActivityC extends AppCompatActivity {

    private TextView tvTime;
    private boolean isStop = false;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        tvTime = findViewById(R.id.tvTime);
        startThread();
    }

    /**
     * 当Activity销毁时线程没有关闭导致的内存泄漏
     * 可赋值为一个引用，有页面销毁时可以调用interrupt即可。
     */
    private void startThread() {
        thread = new Thread() {
            public void run() {
                while (!isStop) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long millis = System.currentTimeMillis();
                            CharSequence currentTime = DateFormat.format("yyyy/MM/dd HH:mm:ss", millis);
                            Log.e("TAG", "currentTime:" + currentTime);
                            tvTime.setText(currentTime);
                        }
                    });
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;//停止线程
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
    }
}
