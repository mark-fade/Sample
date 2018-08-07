package com.example.data.bean.test;

import java.util.ArrayList;
import java.util.List;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class TestBean {
    /**
     * shidu : 49%
     * pm25 : 11.0
     * pm10 : 25.0
     * quality : 优
     * wendu : -11
     * ganmao : 各类人群可自由活动
     * yesterday : {"date":"25日星期四","sunrise":"07:30","high":"高温 -3.0℃","low":"低温 -11.0℃","sunset":"17:24","aqi":31,"fx":"东北风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"}
     * forecast : [{"date":"26日星期五","sunrise":"07:29","high":"高温 -3.0℃","low":"低温 -10.0℃","sunset":"17:25","aqi":20,"fx":"西南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"27日星期六","sunrise":"07:28","high":"高温 -1.0℃","low":"低温 -9.0℃","sunset":"17:26","aqi":82,"fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"28日星期日","sunrise":"07:28","high":"高温 0.0℃","low":"低温 -8.0℃","sunset":"17:28","aqi":34,"fx":"北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"29日星期一","sunrise":"07:27","high":"高温 1.0℃","low":"低温 -7.0℃","sunset":"17:29","aqi":35,"fx":"西南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"30日星期二","sunrise":"07:26","high":"高温 4.0℃","low":"低温 -4.0℃","sunset":"17:30","aqi":47,"fx":"西南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"}]
     */

    public String shidu = "";
    public double pm25;
    public double pm10;
    public String quality = "";
    public String wendu = "";
    public String ganmao = "";
    public YesterdayBean yesterday = new YesterdayBean();
    public List<ForecastBean> forecast = new ArrayList<>();

    public static class YesterdayBean {
        /**
         * date : 25日星期四
         * sunrise : 07:30
         * high : 高温 -3.0℃
         * low : 低温 -11.0℃
         * sunset : 17:24
         * aqi : 31.0
         * fx : 东北风
         * fl : <3级
         * type : 多云
         * notice : 阴晴之间，谨防紫外线侵扰
         */

        public String date = "";
        public String sunrise = "";
        public String high = "";
        public String low = "";
        public String sunset = "";
        public double aqi;
        public String fx = "";
        public String fl = "";
        public String type = "";
        public String notice = "";

    }

    public static class ForecastBean {
        /**
         * date : 26日星期五
         * sunrise : 07:29
         * high : 高温 -3.0℃
         * low : 低温 -10.0℃
         * sunset : 17:25
         * aqi : 20.0
         * fx : 西南风
         * fl : <3级
         * type : 晴
         * notice : 愿你拥有比阳光明媚的心情
         */

        public String date = "";
        public String sunrise = "";
        public String high = "";
        public String low = "";
        public String sunset = "";
        public double aqi;
        public String fx = "";
        public String fl = "";
        public String type = "";
        public String notice = "";

    }

    @Override
    public String toString() {
        return "TestBean{" +
                "shidu='" + shidu + '\'' +
                ", pm25=" + pm25 +
                ", pm10=" + pm10 +
                ", quality='" + quality + '\'' +
                ", wendu='" + wendu + '\'' +
                ", ganmao='" + ganmao + '\'' +
                ", yesterday=" + yesterday +
                ", forecast=" + forecast +
                '}';
    }
}
