package com.fineeat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fs, String tts){
        fragments.add(fs);
        tabTitles.add(tts);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
