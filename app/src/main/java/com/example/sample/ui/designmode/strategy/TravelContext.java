package com.example.sample.ui.designmode.strategy;

/*****************************   
 * @作者：chenk
 * @描述：
 ******************************/

public class TravelContext {

    Strategy strategy;

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void travel() {
        if (strategy != null) {
            strategy.travel();
        }
    }

}
