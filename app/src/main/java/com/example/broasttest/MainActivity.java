package com.example.broasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
            private IntentFilter intentFilter;
            private LocalBroadcastManager localBroadcastManager;
            private LocalReceiver localReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                 //把布局弄进来
        localBroadcastManager = localBroadcastManager.getInstance(this);//获取实例
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent  = new Intent("com.example.broadcasttest.MY_BROADCAST");//通过Intent构造一个广播
                localBroadcastManager.sendBroadcast(intent);    //通过sendBroadcast(intent)发送广播。
            }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");//为IntentFilter添加action；此广播表示本地广播
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);//注册
    }
    protected  void onDestroy(){
        super.onDestroy();                                      //动态注册广播一定要取消注册
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
    class LocalReceiver extends  BroadcastReceiver{             //你奶奶的熊这里LocalReceiver首字母大写
        public void onReceive(Context context, Intent intent){  //你大爷的这里receive没有r
            Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
        }
    }
}                           //本地广播无法通过静态注册的方式进行接收。静态注册主要是为了在程序未启动的情况下也能接收广播。而发送本地广播时，程序肯定已经启动。
