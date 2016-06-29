package com.xm.dagger2demo.module;

import com.xm.dagger2demo.Test3;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;
import dagger.multibindings.LongKey;
import dagger.multibindings.StringKey;

/**
 * Created by Administrator on 2016/6/29.
 */
@Module
public class MyModule {
    @Provides
    @IntoMap
    @StringKey("foo")
    static Long provideFooValue() {
        return 100L;
    }
    @Provides
    @IntoMap
    @StringKey("goo")
    static Long provideGooValue() {
        return 100L;
    }
    @Provides
    @IntoMap
    @IntKey(1)
    static int provideIntValue() {
        return 100;
    }
    @Provides
    @IntoMap
    @LongKey(1L)
    static Long provideLongValue() {
        return 100L;
    }
    @Provides
    @IntoMap
    @ClassKey(Test3.class)
    static String provideTest3Value() {
        return "value for Test3";
    }
}