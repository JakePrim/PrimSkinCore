package com.prim.lib_skin.java;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

/**
 * @author prim
 * @version 1.0.0
 * @desc 被观察者
 * @time 2019-09-11 - 10:30
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class SkinManager extends Observable {
    private static SkinManager instance;

    private Application application;

    public static SkinManager init(Application application) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }
        return instance;
    }

    public static SkinManager getInstance() {
        return instance;
    }

    private SkinManager(Application application) {
        this.application = application;
        SkinPreference.init(application);
        SkinResources.init(application);
        //监听所有的activity生命周期
        application.registerActivityLifecycleCallbacks(new SkinActivityLifecycle());
        loadSkin(SkinPreference.getInstance().getSkin());
    }

    /**
     * 加载皮肤包
     *
     * @param path
     */
    public void loadSkin(String path) {
        if (path == null || path.isEmpty()) {
            SkinPreference.getInstance().setSkin("");
            SkinResources.getInstance().reset();
        } else {
            try {
                //获取资源管理器 加载皮肤包中的资源
                AssetManager assetManager = AssetManager.class.newInstance();
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.setAccessible(true);
                addAssetPath.invoke(assetManager, path);

                //加载皮肤包里的资源

                //获取当前应用的资源
                Resources resources = application.getResources();

                //皮肤包的资源
                Resources skinResources = new Resources(assetManager,
                        resources.getDisplayMetrics(), resources.getConfiguration());

                //加载皮肤包资源
                //皮肤包的包名
                PackageManager packageManager = application.getPackageManager();
                //获取一个apk的包名
                PackageInfo info = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
                String packageName = info.packageName;
                //更换为皮肤包资源
                SkinResources.getInstance().applySkin(skinResources, packageName);
                //记录使用的皮肤包
                SkinPreference.getInstance().setSkin(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //通知观察者 //应用皮肤包
        setChanged();
        notifyObservers();
    }


}
