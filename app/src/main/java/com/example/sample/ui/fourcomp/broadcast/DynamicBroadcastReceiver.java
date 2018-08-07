package com.example.sample.ui.fourcomp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*****************************   
 * @作者：chenk
 * @描述：
 ******************************/

public class DynamicBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            listener.onReceive(context, intent);
        }
    }

    private DynamicBroadcastReceiverListener listener;

    public void setListener(DynamicBroadcastReceiverListener listener) {
        this.listener = listener;
    }

    public interface DynamicBroadcastReceiverListener {
        void onReceive(Context context, Intent intent);
    }
}
