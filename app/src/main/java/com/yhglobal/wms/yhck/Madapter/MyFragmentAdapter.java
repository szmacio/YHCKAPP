package com.yhglobal.wms.yhck.Madapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Macio on 2017/7/21.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private  List<Fragment> mFragments;
    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public  MyFragmentAdapter(FragmentManager fm,List<Fragment> list){
        super(fm);
        this.mFragments=list;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
//        return list.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
