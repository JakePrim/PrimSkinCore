package com.prim.primskincore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @desc
 * @author prim
 * @time 2019-09-17 - 13:41
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 * @version 1.0.0
 */
class MyFragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video,container,false)
    }
}