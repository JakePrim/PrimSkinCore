package com.prim.lib_skin.java;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.core.view.LayoutInflaterCompat;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc 监听所有activity的生命周期回调
 * @time 2019-09-11 - 22:30
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private HashMap<Activity, SkinLayoutFactory> mLayoutFactoryMap = new HashMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //更新状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SkinThemeUtils.updateStatusBar(activity);
        }
        Typeface typeface = SkinThemeUtils.updateTypeface(activity);


        //拿到每个activity的布局加载器
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        //mFactorySet 如果为true 抛出异常，有可能会出现这种情况，通过反射将mFactorySet设置为false
        try {
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //自定义布局处理工厂
        SkinLayoutFactory factory = new SkinLayoutFactory(typeface, activity);
        //设置工厂
        LayoutInflaterCompat.setFactory2(layoutInflater, factory);

        //注册观察者
        mLayoutFactoryMap.put(activity, factory);
        SkinManager.getInstance().addObserver(factory);

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        SkinLayoutFactory layoutFactory = mLayoutFactoryMap.remove(activity);
        if (layoutFactory != null) {
            SkinManager.getInstance().deleteObserver(layoutFactory);
        }
    }

    public void updateSkin(Activity activity) {
        SkinLayoutFactory skinLayoutInflaterFactory = mLayoutFactoryMap.get(activity);
        if (skinLayoutInflaterFactory != null) {
            skinLayoutInflaterFactory.update(null, null);
        }
    }
}
