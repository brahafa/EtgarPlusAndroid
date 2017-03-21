package com.plus.etgar.etgarapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.plus.etgar.etgarapp.fragments.AddProductPagerFragment;

/**
 * Created by davidgd on 02/21/2017.
 */

public class AddProductPagerAdapter  extends android.support.v4.app.FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "אופניים", "אביזרים", "חלפים" };
    private Context context;

    public AddProductPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return AddProductPagerFragment.newInstance(0,"");
            case 1: return AddProductPagerFragment.newInstance(1,"");
            case 2: return AddProductPagerFragment.newInstance(2,"");
            default: return AddProductPagerFragment.newInstance(2,"");
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}

