package com.prim.primskincore

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.prim.lib_skin.java.SkinManager


/**
 * @desc
 * @author prim
 * @time 2019-09-16 - 16:04
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 * @version 1.0.0
 */
class SkinActivity : AppCompatActivity() {

    var skins = ArrayList<Skin>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skin)
        skins.add(Skin("app-skin-debug.skin","e0893ca73a972d82bcfc3a5a7a83666d","https://www.baidu.com"))
    }

    fun change(view: View) {
        SkinManager.getInstance().loadSkin("/sdcard/app-skin-debug.skin")
    }

    fun restore(view: View) {
        SkinManager.getInstance().loadSkin("")
    }
}