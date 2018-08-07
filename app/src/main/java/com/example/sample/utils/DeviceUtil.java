package com.example.sample.utils;

import android.os.Build;

import java.util.UUID;

/**
 * Created by dell on 2017/12/19.
 */

public class DeviceUtil {
    /**
     * 获得唯一的设备id
     * 参考网址：http://blog.csdn.net/w371500241/article/details/70138872
     * @return
     */
    public static String getDeviceId()
    {
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial = null;
        try
        {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        }
        catch (Exception e)
        {
            serial = "serial"; // some value
        }

        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}
