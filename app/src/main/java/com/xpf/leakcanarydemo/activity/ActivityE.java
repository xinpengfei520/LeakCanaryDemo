package com.xpf.leakcanarydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xpf.leakcanarydemo.R;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityE extends AppCompatActivity {

    private MyTimerTask task;
    private Timer timer;
    private static boolean isStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e);
        startTimer();
    }

    private void startTimer() {
        // 改写前的：
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                while (true) ;
//            }
//        }, 1000);  // 1秒后启动一个任务

        // 改写后的：
        timer = new Timer();
        task = new MyTimerTask();
        timer.schedule(task, 1000);
    }

    /**
     * 这里内存泄漏在于Timer和TimerTask没有进行Cancel，从而导致Timer和TimerTask一直引用外部类Activity
     * TimerTask内存泄漏可以使用在适当的时机进行Cancel。经过测试，
     * 证明单单使用在适当的时机进行Cancel ， 还是有内存泄漏的问题。
     * 所以一定要用静态内部类配合使用。
     */
    private static class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            while (!isStop) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "timerTask");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
        isStop = true;
        if (task != null) {
            task.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
    }
}
