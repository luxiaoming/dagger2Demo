package com.xm.dagger2demo.module;

import android.content.Context;
import android.location.LocationManager;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

/**
 * Created by Administrator on 2016/6/29.
 */
@Module
public class ModuleA{
    @Provides
    @IntoSet
    static String provideOneString(LocationManager depA, Context depB) {
        return "ABC";
    }
}