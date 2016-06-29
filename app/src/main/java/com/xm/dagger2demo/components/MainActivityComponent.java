package com.xm.dagger2demo.components;

import com.xm.dagger2demo.MainActivity;
import com.xm.dagger2demo.PerActivity;
import com.xm.dagger2demo.module.ModuleA;

import dagger.Component;

/**
 * Created by Administrator on 2016/6/27.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules =ModuleA.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
