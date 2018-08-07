package com.example.sample.ui.fourcomp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*****************************   
 * @作者：chenk
 * @描述：
 ******************************/

public class StaticBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("111","接受到其他app广播="+intent.getStringExtra("name"));
    }
}
