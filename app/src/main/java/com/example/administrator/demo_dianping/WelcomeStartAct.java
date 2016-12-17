package com.example.administrator.demo_dianping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.administrator.demo_dianping.utils.SharedUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动应用的欢迎界面, 延时显示3秒
 * 两种实现方式
 * 1.使用Handler
 * 2.使用Timer
 */

public class WelcomeStartAct extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置欢迎界面的图片
        setContentView(R.layout.welcome);

        //        //Handler设置延时跳转
//        new Handler(new Handler.Callback() {
//            //3秒后接收到handler传入的消息, 并启动Acitvity
//            @Override
//            public boolean handleMessage(Message msg) {
//                //跳转Activity
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                return false;
//            }
//            //延时3秒发送一条空消息
//        }).sendEmptyMessageDelayed(0, 3000);

        //使用Java中的定时器进行处理
        Timer timer = new Timer();
        timer.schedule(new Task(), 3000);  //定时器执行延时任务的方法
    }

    //schedule中传入的TimerTask对象
    class Task extends TimerTask {
        @Override
        public void run() {
            //实现页面的跳转
            //不是第一次启动, 跳转到主页面, isFirst默认为false
            if (SharedUtils.getWelcomeBoolean(getBaseContext())) {  //if true
                startActivity(new Intent(WelcomeStartAct.this, MainActivity.class));
                finish();
                //是第一次启动,跳转到导航
            } else {
                startActivity(new Intent(getBaseContext(), WelcomeGuideAct.class));
                //保存访问记录, 将isFirst设置为true
                SharedUtils.putWelcomeBoolean(getBaseContext(), true);
            }
            finish();
        }
    }
}



