package com.spinecore.hack.medipiandroid.store;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.spinecore.hack.medipiandroid.InstructionFragment;

/**
 * Created by Aaron.Gibbon on 28/09/2017.
 */

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {

    public CollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        InstructionFragment fragment = InstructionFragment.newInstance();
        Bundle args = new Bundle();
        args.putInt(InstructionFragment.ARG_OBJECT, i+1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
