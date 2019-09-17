package com.prim.primskincore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabLayout = findViewById<MyTabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)

        val list = arrayListOf<Fragment>()
        list.add(MyFragment1())
        list.add(MyFragment2())
        list.add(MyFragment3())
        val listTitle = ArrayList<String>()
        listTitle.add("音乐")
        listTitle.add("视频")
        listTitle.add("电台")
        val myFragmentPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager, list, listTitle)
        viewPager.setAdapter(myFragmentPagerAdapter)
        tabLayout.setupWithViewPager(viewPager)
    }

    fun skinSelect(view: View) {
        val intent = Intent(this, SkinActivity::class.java)
        startActivity(intent)
    }
}
