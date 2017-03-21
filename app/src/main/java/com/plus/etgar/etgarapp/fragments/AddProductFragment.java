package com.plus.etgar.etgarapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.plus.etgar.etgarapp.R;
import com.plus.etgar.etgarapp.adapters.AddProductPagerAdapter;

public class AddProductFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_product, container, false);
        initViewPager();
        return view;
    }

    private void initViewPager() {

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new AddProductPagerAdapter(getActivity().getSupportFragmentManager(), getActivity()));

        // Give the TabLayout the ViewPager
       // TableLayout tabLayout = (TableLayout)view.findViewById(R.id.sliding_tabs);
        //tabLayout.setupWithViewPager(viewPager);
    }

}
