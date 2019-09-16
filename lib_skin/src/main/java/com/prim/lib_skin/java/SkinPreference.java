package com.prim.lib_skin.java;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-12 - 23:09
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class SkinPreference {
    private static final String SKIN_SHARED = "skins";

    private static final String KEY_SKIN_PATH = "skin-path";
    private static SkinPreference instance;
    private final SharedPreferences mPref;

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinPreference.class) {
                if (instance == null) {
                    instance = new SkinPreference(context.getApplicationContext());
                }
            }
        }
    }

    public static SkinPreference getInstance() {
        return instance;
    }

    private SkinPreference(Context context) {
        mPref = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE);
    }

    public void setSkin(String skinPath) {
        mPref.edit().putString(KEY_SKIN_PATH, skinPath).apply();
    }

    public String getSkin() {
        return mPref.getString(KEY_SKIN_PATH, null);
    }

}
