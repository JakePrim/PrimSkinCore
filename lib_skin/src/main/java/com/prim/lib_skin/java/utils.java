package com.prim.lib_skin.java;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-12 - 22:44
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class utils {
    public static int[] getResId(Context context, int[] attrs) {
        int[] resIds = new int[attrs.length];
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < typedArray.length(); i++) {
            resIds[i] = typedArray.getResourceId(i, 0);//拿到属性ID
        }
        typedArray.recycle();
        return resIds;
    }
}
