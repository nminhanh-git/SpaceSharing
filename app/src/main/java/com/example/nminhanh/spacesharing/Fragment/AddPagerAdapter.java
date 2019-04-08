package com.example.nminhanh.spacesharing.Fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class AddPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mFragmentList;

    public AddPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new AddAddressFragment();
            case 1:
                return new AddDescriptionFragment();
            case 2:
                return new AddOtherFragment();
        }
        return null;
//        return mFragmentList.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
