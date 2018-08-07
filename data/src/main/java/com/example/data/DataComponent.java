package com.example.data;

import javax.inject.Singleton;

import dagger.Component;

/*****************************
 * @作者：chenk
 * @描述：数据层 Component
 ******************************/

@Singleton
@Component(modules = {DataModule.class})
public interface DataComponent {

    void inject(DataManager dataManager);

}
