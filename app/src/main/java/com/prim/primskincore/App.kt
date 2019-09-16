package com.prim.primskincore

import android.app.Application
import com.prim.lib_skin.java.SkinManager

/**
 * @desc
 * @author prim
 * @time 2019-09-16 - 14:27
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 * @version 1.0.0
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
    }
}