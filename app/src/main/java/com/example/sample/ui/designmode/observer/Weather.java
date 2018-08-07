package com.example.sample.ui.designmode.observer;

/*****************************   
 * @作者：chenk
 * @描述：
 ******************************/

public class Weather {

    private String description;

    public Weather(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "description='" + description + '\'' +
                '}';
    }

}
