package com.xpf.leakcanarydemo.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xpf.leakcanarydemo.R;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAsyncTask();
            }
        });
    }

    /**
     * AsyncTask匿名内部类导致的内存泄漏：
     *
     * 由于匿名内部类会隐式的持有外部类的引用
     *
     * 当AsyncTask任务还没有完成时，手机屏幕旋转导致Activity生命周期变化，
     * 例如从竖屏切换到横屏时，旧Activity先销毁，新Activity重新创建，由于后台任务还没执行完，
     * 而它有持有Activity的示例，导致销毁的Activity无法被GC回收，从而导致Activity实例泄漏！
     */
    @SuppressLint("StaticFieldLeak")
    private void startAsyncTask() {
        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e("TAG", "AsyncTask onPreExecute()");
            }

            @Override
            protected Void doInBackground(Void... params) {
                // Do some slow work in background
                SystemClock.sleep(20000);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.e("TAG", "AsyncTask onPostExecute()");
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy()");
    }
}
