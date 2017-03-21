package com.plus.etgar.etgarapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.plus.etgar.etgarapp.fragments.DashbordFragment;
import com.plus.etgar.etgarapp.fragments.ProfileFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //change
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6339381307749382~2022268553");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new DashbordFragment()).commit();

    }

    public void openFragment(Fragment fragment){

        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,fragment,"")
                .addToBackStack(null)
                .commit();
    }

    public void openFragment(Fragment fragment, Bundle bundle){


        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,fragment,"")
                .addToBackStack(null)
                .commit();
    }
    public void clearBackStack(){
        this.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);    }
}
