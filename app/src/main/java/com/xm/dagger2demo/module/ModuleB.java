package com.xm.dagger2demo.module;

import android.content.Context;
import android.location.LocationManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;

/**
 * Created by Administrator on 2016/6/29.
 */
@Module
public class ModuleB {

    @Provides
    @ElementsIntoSet
    static Set<String> provideSomeStrings(LocationManager depA, Context depB) {
        return new HashSet<String>(Arrays.asList("DEF", "GHI"));
    }
}