package com.prim.lib_skin.java;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.prim.lib_skin.R;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-12 - 22:44
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class SkinThemeUtils {
    public static int[] getResId(Context context, int[] attrs) {
        int[] resIds = new int[attrs.length];
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < typedArray.length(); i++) {
            resIds[i] = typedArray.getResourceId(i, 0);//拿到属性ID
        }
        typedArray.recycle();
        return resIds;
    }

    private static int[] APPCOMPAT_COLOR_PRINARY_DARK_ATIRS = {
            androidx.appcompat.R.attr.colorPrimaryDark
    };

    //高优先级
    private static int[] STATUSBAR_COLOR_ATTRS = {
            android.R.attr.statusBarColor, android.R.attr.navigationBarColor
    };

    private static int[] TYPRFACE_ATTR = {R.attr.skinTypeFace};

    //更新状态栏
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void updateStatusBar(Activity activity) {
        int[] statusBarColorIDs = getResId(activity, STATUSBAR_COLOR_ATTRS);
        if (statusBarColorIDs[0] == 0) {//如果没有配置状态栏颜色则读取colorPrimaryDark
            int statusBarColorID = getResId(activity, APPCOMPAT_COLOR_PRINARY_DARK_ATIRS)[0];
            if (statusBarColorID != 0) {
                //读取皮肤包中的颜色值
                int color = SkinResources.getInstance().getColor(statusBarColorID);
                activity.getWindow().setStatusBarColor(color);
            }
        } else {
            activity.getWindow().setStatusBarColor(SkinResources.getInstance().getColor(statusBarColorIDs[0]));
        }
        if (statusBarColorIDs[1] != 0) {
            activity.getWindow().setNavigationBarColor(SkinResources.getInstance().getColor
                    (statusBarColorIDs[1]));
        }
    }

    public static Typeface updateTypeface(Activity activity) {
        int skinTypefaceId = getResId(activity, TYPRFACE_ATTR)[0];
        return SkinResources.getInstance().getTypeface(skinTypefaceId);
    }
}
