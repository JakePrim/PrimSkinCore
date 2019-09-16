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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skin)
    }

    fun change(view: View) {

    }

    fun restore(view: View) {
        SkinManager.getInstance().loadSkin("")
    }
}