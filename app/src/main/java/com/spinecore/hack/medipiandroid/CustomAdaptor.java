package com.spinecore.hack.medipiandroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Sanjay on 28/09/2017.
 */

public class CustomAdaptor extends FragmentStatePagerAdapter {
    public CustomAdaptor(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return BlankFragment.newInstance("bp");
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        return "Question  " + (++position);
    }

}
