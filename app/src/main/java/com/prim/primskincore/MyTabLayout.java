package com.prim.primskincore;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.google.android.material.tabs.TabLayout;
import com.prim.lib_skin.java.SkinResources;
import com.prim.lib_skin.java.SkinViewSupport;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-17 - 13:49
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class MyTabLayout extends TabLayout implements SkinViewSupport {

    int tabIndicatorColorResId;
    int tabTextColorResId;

    public MyTabLayout(Context context) {
        this(context, null, 0);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayout,
                defStyleAttr, 0);
        tabIndicatorColorResId = a.getResourceId(R.styleable.TabLayout_tabIndicatorColor, 0);
        tabTextColorResId = a.getResourceId(R.styleable.TabLayout_tabTextColor, 0);
        a.recycle();
    }

    @Override
    public void applySkin() {
        if (tabIndicatorColorResId != 0) {
            int color = SkinResources.getInstance().getColor(tabIndicatorColorResId);
            setSelectedTabIndicatorColor(color);
        }

        if (tabTextColorResId != 0) {
            ColorStateList colorStateList = SkinResources.getInstance().getColorStateList(tabTextColorResId);
            setTabTextColors(colorStateList);
        }
    }
}
