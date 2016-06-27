package com.xm.dagger2demo.components;

import com.xm.dagger2demo.Test3;
import com.xm.dagger2demo.module.CommonModule;

import javax.inject.Singleton;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2016/6/27.
 */

@Singleton
@Subcomponent(modules = CommonModule.class)
public interface CommonComponent {
     Test3 getTest3();
}
