package com.prim.lib_skin.java;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 修改view的属性
 * @time 2019-09-12 - 22:23
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class SkinAttribute {

    private static final List<String> mAttributes = new ArrayList<>();

    private List<SkinView> skinViews = new ArrayList<>();

    static {
        mAttributes.add("background");
        mAttributes.add("src");

        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableTop");
        mAttributes.add("drawableRight");
        mAttributes.add("drawableBottom");
        mAttributes.add("skinTypeFace");
    }

    private Typeface typeface;

    public SkinAttribute(Typeface typeface) {
        this.typeface = typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    private static final String TAG = "SkinAttribute";

    public void load(View view, AttributeSet attrs) {
        List<SkinPair> mSkinPars = new ArrayList<>();
        //获取属性
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //获取属性名
            String attributeName = attrs.getAttributeName(i);
            Log.e(TAG, "   " + attributeName);
            //匹配要修改的属性名
            if (mAttributes.contains(attributeName)) {
                //获取属性值
                String attributeValue = attrs.getAttributeValue(i);
                if (attributeValue.startsWith("#")) {//写死的先不去管 #000000
                    continue;
                }
                int resId;
                if (attributeValue.startsWith("?")) {
                    //attrId
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    resId = SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                } else {
                    //@1232311
                    resId = Integer.parseInt(attributeValue.substring(1));
                }
                //将匹配的都存储下来
                SkinPair skinPair = new SkinPair(attributeName, resId);
                mSkinPars.add(skinPair);
            }
        }
        //将布局中每个view与之对应的属性集合放入到对应的view集合中
        if (!mSkinPars.isEmpty()) {
            SkinView skinView = new SkinView(view, mSkinPars);
            skinView.applySkin(typeface);
            skinViews.add(skinView);
        } else if (view instanceof TextView || view instanceof SkinViewSupport) {
            //没有属性满足 但是需要修改字体
            SkinView skinView = new SkinView(view, mSkinPars);
            skinView.applySkin(typeface);
            skinViews.add(skinView);
        }
    }

    /**
     * 更换皮肤
     */
    public void applySkin() {
        //遍历保存的表然后换皮肤
        for (SkinView skinView : skinViews) {
            skinView.applySkin(typeface);
        }
    }

    /**
     * 保存每个view要修改的属性值列表
     */
    static class SkinView {
        View view;
        List<SkinPair> skinPairs;

        public SkinView(View view, List<SkinPair> skinPairs) {
            this.view = view;
            this.skinPairs = skinPairs;
        }

        private static final String TAG = "SkinView";

        public void applySkin(Typeface typeface) {
            applySkinTypeface(typeface);
            applySkinSupportView();
            for (SkinPair skinPair : skinPairs) {
                Drawable left = null, right = null, top = null, bottom = null;
                Log.e(TAG, "applySkin: " + skinPair.attrName);
                switch (skinPair.attrName) {
                    case "background":
                        Object background = SkinResources.getInstance().getBackground(skinPair.resId);
                        //可能是一个图片或者颜色
                        if (background instanceof Integer) {
                            //color 选择器
                            view.setBackgroundColor((Integer) background);
                        } else {
                            ViewCompat.setBackground(view, (Drawable) background);
                        }
                        break;
                    case "src":
                        if (view instanceof ImageView) {
                            ImageView imageView = (ImageView) view;
                            Object background1 = SkinResources.getInstance().getBackground(skinPair.resId);
                            if (background1 instanceof Integer) {
                                imageView.setImageDrawable(new ColorDrawable((Integer) background1));
                            } else {
                                imageView.setImageDrawable((Drawable) background1);
                            }
                        }
                        break;
                    case "textColor":
                        int color = SkinResources.getInstance().getColor(skinPair.resId);
                        if (view instanceof TextView) {
                            TextView textView = (TextView) view;
                            textView.setTextColor(color);
                        }
                        break;
                    case "drawableLeft":
                        left = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableTop":
                        top = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableRight":
                        right = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableBottom":
                        bottom = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "skinTypeFace"://布局的字体更换
                        Typeface typeface1 = SkinResources.getInstance().getTypeface(skinPair.resId);
                        applySkinTypeface(typeface1);
                        break;
                }
                if (null != left || null != right || null != top || null != bottom) {
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right,
                            bottom);
                }
            }
        }

        private void applySkinSupportView() {
            if (view instanceof SkinViewSupport) {
                ((SkinViewSupport) view).applySkin();
            }
        }

        private void applySkinTypeface(Typeface typeface) {
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            }
        }
    }

    static class SkinPair {
        String attrName;
        int resId;

        public SkinPair(String attrName, int resId) {
            this.attrName = attrName;
            this.resId = resId;
        }
    }
}
