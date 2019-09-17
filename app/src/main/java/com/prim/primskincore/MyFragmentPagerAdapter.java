package com.prim.primskincore;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-17 - 13:56
 * @contact https://jakeprim.cn
 * @name PrimSkinCore
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> title;
    private ArrayList<Fragment> fragments;

//    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, ArrayList<String> title, ArrayList<Fragment> fragments) {
//        super(fm);
//        this.title = title;
//        this.fragments = fragments;
//    }

    public MyFragmentPagerAdapter(@NotNull FragmentManager supportFragmentManager, @NotNull ArrayList<Fragment> list, @NotNull ArrayList<String> listTitle) {
        super(supportFragmentManager);
        this.title = listTitle;
        this.fragments = list;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
