package com.example.nminhanh.spacesharing.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AddPagerAdapter extends FragmentPagerAdapter {
    public AddPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new AddAddressFragment();
            case 1:
                return new AddDescriptionFragment();
            case 2:
        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
