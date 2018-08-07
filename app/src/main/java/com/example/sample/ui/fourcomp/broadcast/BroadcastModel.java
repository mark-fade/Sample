package com.example.sample.ui.fourcomp.broadcast;

import android.content.Intent;

import com.example.sample.base.BaseModel;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class BroadcastModel implements BaseModel {

    public String handleReceiver(Intent intent) {
        StringBuffer sb = new StringBuffer();
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            if (intent.hasExtra("state")) {
                if (intent.getIntExtra("state", 0) == 0) {
                    sb.append("耳机拔出--");
                } else {
                    sb.append("耳机插入---");
                }
            }
        } else if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (intent.hasExtra("state")) {
                if (intent.getIntExtra("state", 0) == 0) {
                    sb.append("网络断开--");
                } else {
                    sb.append("网络连接---");
                }
            }
        } else if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            if (intent.hasExtra("state")) {
                if (intent.getIntExtra("state", 0) == 0) {
                    sb.append("飞行模式-开--");
                } else {
                    sb.append("飞行模式-关--");
                }
            }
        }
        sb.append(intent.getExtras().toString());
        return sb.toString();
    }

}
