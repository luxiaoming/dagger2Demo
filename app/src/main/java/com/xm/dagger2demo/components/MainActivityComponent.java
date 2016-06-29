package com.xm.dagger2demo.components;

import com.xm.dagger2demo.MainActivity;
import com.xm.dagger2demo.PerActivity;
import com.xm.dagger2demo.module.ModuleA;
import com.xm.dagger2demo.module.ModuleB;
import com.xm.dagger2demo.module.MyModule;
import com.xm.dagger2demo.module.MyModule2;

import java.util.Map;
import java.util.Set;

import dagger.Component;

/**
 * Created by Administrator on 2016/6/27.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules ={MyModule2.class,ModuleB.class,ModuleA.class,MyModule.class})
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
    Set<String> strings();
    Map<String, Long> longsByString();

    Map<MyModule2.MyEnum, String> StringsByMyEnum();
}
