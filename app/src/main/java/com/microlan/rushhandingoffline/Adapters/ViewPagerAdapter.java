package com.microlan.rushhandingoffline.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.microlan.rushhandingoffline.Fragments.FragmentNewCounter;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FragmentNewCounter fragment = null;
        if (position == 0)
        {
            fragment = new FragmentNewCounter();
        }
        else if (position == 1)
        {
            fragment = new FragmentNewCounter();
        }
        else if (position == 2)
        {
            fragment = new FragmentNewCounter();
        }
        else if (position == 3)
        {
            fragment = new FragmentNewCounter();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Vegetables";
        }
        else if (position == 1)
        {
            title = "Flours";
        }
        else if (position == 2)
        {
            title = "Cold Drinks";
        }
        else if (position == 3)
        {
            title = "Meats";
        }
        return title;
    }
}
