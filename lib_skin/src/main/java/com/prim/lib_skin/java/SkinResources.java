package com.prim.lib_skin.java;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * @author prim
 * @version 1.0.0
 * @desc 皮肤资源管理
 * @time 2019-09-12 - 23:16
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class SkinResources {
    //皮肤的资源
    private Resources mSkinResources;
    //当前应用的资源
    private Resources mAppResources;
    //皮肤包名
    private String mSkinName;
    //是否使用默认皮肤
    private boolean isDefalueSkin = true;

    public static SkinResources instance;

    public static void init(Context context) {
        if (null == instance) {
            synchronized (SkinResources.class) {
                if (null == instance) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }

    public SkinResources(Context context) {
        mAppResources = context.getResources();
    }

    public void reset() {
        mSkinResources = null;
        mSkinName = "";
        isDefalueSkin = true;
    }

    public void applySkin(Resources resources, String name) {
        mSkinResources = resources;
        mSkinName = name;
        isDefalueSkin = false;
    }

    /**
     * 通过资源ID 获取皮肤包中对应的资源ID 然后进行设置
     *
     * @param resId
     * @return
     */
    public int getIdentfier(int resId) {
        if (isDefalueSkin) {
            return resId;
        }

        //通过当前应用资源获取到 资源名 和 资源类型，然后通过资源名  资源类型 资源ID 获取到皮肤包中的资源ID
        String resourceName = mAppResources.getResourceName(resId);
        String resourceTypeName = mAppResources.getResourceTypeName(resId);
        //getIdentifier ?? 获取到皮肤包对应到资源ID
        int skinId = mSkinResources.getIdentifier(resourceName, resourceTypeName, mSkinName);

        return skinId;
    }

    public int getColor(int resId){
        if (isDefalueSkin){
            return mAppResources.getColor(resId);
        }
        int skinId = getIdentfier(resId);
        if (skinId == 0){
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(skinId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDefalueSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentfier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    public Drawable getDrawable(int resId) {
        //如果有皮肤  isDefaultSkin false 没有就是true
        if (isDefalueSkin) {
            return mAppResources.getDrawable(resId);
        }
        int skinId = getIdentfier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }


    /**
     * 可能是Color 也可能是drawable
     *
     * @return
     */
    public Object getBackground(int resId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resId);

        if (resourceTypeName.equals("color")) {
            return getColor(resId);
        } else {
            // drawable
            return getDrawable(resId);
        }
    }

    public String getString(int resId) {
        try {
            if (isDefalueSkin) {
                return mAppResources.getString(resId);
            }
            int skinId = getIdentfier(resId);
            if (skinId == 0) {
                return mAppResources.getString(skinId);
            }
            return mSkinResources.getString(skinId);
        } catch (Resources.NotFoundException e) {

        }
        return null;
    }

    public Typeface getTypeface(int resId) {
        String skinTypefacePath = getString(resId);
        if (TextUtils.isEmpty(skinTypefacePath)) {
            return Typeface.DEFAULT;
        }
        try {
            Typeface typeface;
            if (isDefalueSkin) {
                typeface = Typeface.createFromAsset(mAppResources.getAssets(), skinTypefacePath);
                return typeface;

            }
            typeface = Typeface.createFromAsset(mSkinResources.getAssets(), skinTypefacePath);
            return typeface;
        } catch (RuntimeException e) {
        }
        return Typeface.DEFAULT;
    }


}
