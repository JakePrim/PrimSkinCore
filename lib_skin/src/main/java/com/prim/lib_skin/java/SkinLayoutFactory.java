package com.prim.lib_skin.java;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-11 - 22:39
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class SkinLayoutFactory implements LayoutInflater.Factory2, Observer {

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app."
    };

    private static final HashMap<String, Constructor<? extends View>> sConstructorMap =
            new HashMap<String, Constructor<? extends View>>();

    private static final Class<?>[] mConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};

    private SkinAttribute skinAttribute;

    public SkinLayoutFactory() {
        skinAttribute = new SkinAttribute();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //参考系统的实现
        View view = createViewFromTag(context, name, attrs);
        if (view == null) {
            view = createView(name, context, attrs);
        }
        //采集view的属性
        skinAttribute.load(view,attrs);
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.contains(".")) {//如果布局xml中的view 名包含.表示为自定义view 此处先不做处理
            return null;
        }
        View view = null;
        for (String prefix : sClassPrefixList) {//遍历view属于哪个包中 然后反射创建view对象
            try {
                view = createView(prefix + name, context, attrs);
                if (view != null) {
                    break;
                }
            } catch (Exception e) {
                // In this case we want to let the base class take a crack
                // at it.
            }
        }
        return view;
    }

    private View createView(String name, Context context, AttributeSet attrs) {
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        try {
            if (constructor == null) {
                Class<? extends View> clazz = context.getClassLoader().loadClass(name).asSubclass(View.class);
                constructor = clazz.getConstructor(mConstructorSignature);
                constructor.setAccessible(true);
                sConstructorMap.put(name, constructor);
            }
        } catch (Exception e) {

        }

        if (null != constructor) {
            try {
                return constructor.newInstance(context, attrs);
            } catch (Exception e) {
            }
        }

        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        skinAttribute.applySkin();
    }
}
