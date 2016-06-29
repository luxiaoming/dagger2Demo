package com.xm.dagger2demo.components;

import android.content.Context;
import android.location.LocationManager;

import com.xm.dagger2demo.DemoApplication;
import com.xm.dagger2demo.module.AndroidModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/6/25.
 */
@Singleton
@Component(modules = AndroidModule.class)
public interface ApplicationComponent {
    void inject(DemoApplication application);
    LocationManager getLocationManager();
    Context getContext();
}
