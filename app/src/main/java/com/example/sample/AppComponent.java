package com.example.sample;

import javax.inject.Singleton;

import dagger.Component;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App app);
}
