package com.example.sample.ui.designmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sample.R;
import com.example.sample.ui.designmode.observer.Observable;
import com.example.sample.ui.designmode.observer.Observer;
import com.example.sample.ui.designmode.observer.Weather;
import com.example.sample.ui.designmode.strategy.Plane;
import com.example.sample.ui.designmode.strategy.Train;
import com.example.sample.ui.designmode.strategy.TravelContext;
import com.example.sample.ui.designmode.strategy.Wallk;
import com.example.sample.utils.ToastUtil;

public class DesignModeActivity extends AppCompatActivity {

    Observable<Weather> observable;
    Observer<Weather> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_mode);
        //观察者模式
        ObservableTest();
        //策略模式
        strategyTest();
    }

    private void ObservableTest() {
        observable = new Observable<Weather>();
        observer = new Observer<Weather>() {
            @Override
            public void onUpdate(Observable<Weather> observable, Weather data) {
                ToastUtil.showMessage(getApplicationContext(),"观察者1：" + data.toString());
            }
        };

        observable.register(observer);

        Weather weather = new Weather("晴转多云");
        observable.notifyObservers(weather);

    }

    private void strategyTest(){
        TravelContext travelContext=new TravelContext();
        travelContext.setStrategy(new Plane());
        travelContext.travel();
        travelContext.setStrategy(new Wallk());
        travelContext.travel();
        travelContext.setStrategy(new Train());
        travelContext.travel();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        observable.unregister(observer);
    }
}
