package com.example.amodule;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AModuleActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_content;
    ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amodule);
        initView();
    }

    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.broadcast_send_button:
                Intent intent = new Intent();
                intent.setAction("android.ck.hello");
                intent.putExtra("name", "147258");
                sendBroadcast(intent);
                break;
            case R.id.bt_provide_get:
                if (contentResolver == null) {
                    contentResolver = this.getContentResolver();
                }
                Uri uri = Uri.parse("content://com.example.sample.ui.fourcomp.contentprovider.MContentProvider/person/2");
                Cursor cursor = contentResolver.query(uri, new String[]{"name", "description"}, null, null, null);
                if (cursor == null) {
                    return;
                }
                StringBuilder cursorResult = new StringBuilder("DB 查询结果：");
                while (cursor.moveToNext()) {
                    String result = cursor.getString(0) + ", " + cursor.getString(1);
                    Log.e("111", "DB 查询结果：" + result);
                    cursorResult.append("\n").append(result);
                }
                tv_content.setText(cursorResult.toString());
                break;
        }
    }
}
