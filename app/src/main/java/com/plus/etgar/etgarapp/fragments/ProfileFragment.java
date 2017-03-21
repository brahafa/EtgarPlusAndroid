package com.plus.etgar.etgarapp.fragments;


import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.plus.etgar.etgarapp.MainActivity;
import com.plus.etgar.etgarapp.R;
import com.plus.etgar.etgarapp.logic.ProductLogic;
import com.plus.etgar.etgarapp.utils.SharePref;
import com.plus.etgar.etgarapp.utils.Utils;

public class ProfileFragment extends Fragment {

    View view;
    TextView textView;
    EditText name, address, phone, comments;
    ImageView logo;
    long start;
    int countClick,counter=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        countClick=0;
        view= inflater.inflate(R.layout.fragment_profile, container, false);
         textView = (TextView) view.findViewById(R.id.save_profile);
         name = (EditText) view.findViewById(R.id.name);
         address = (EditText) view.findViewById(R.id.address);
         phone = (EditText) view.findViewById(R.id.phone);
         comments = (EditText) view.findViewById(R.id.comments);
        logo = (ImageView) view.findViewById(R.id.logo);
        initUi();
        return view;
    }

    private void initUi() {
        final SharePref sharePref = SharePref.getInstance(getContext());


        name.setText(sharePref.getData(Utils.PREF_NAME));
        phone.setText(sharePref.getData(Utils.PREF_PHONE));
        address.setText(sharePref.getData(Utils.PREF_ADDRESS));
        comments.setText(sharePref.getData(Utils.PREF_COMMENTS));

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter==0) {
                    start = System.currentTimeMillis();
                }
                if(counter==10 && System.currentTimeMillis()-start<7000){
                    ProductLogic.getInstance().setManager(true);
                    Utils.openPopupAlert(getContext());

                }
                counter++;

                if(System.currentTimeMillis()-start>7000){
                    counter=0;
                }

            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePref.saveData(Utils.PREF_NAME, name.getText().toString());
                sharePref.saveData(Utils.PREF_PHONE, phone.getText().toString());
                sharePref.saveData(Utils.PREF_ADDRESS, address.getText().toString());
                sharePref.saveData(Utils.PREF_COMMENTS, comments.getText().toString());

                ((MainActivity)getActivity()).clearBackStack();
                ((MainActivity)getActivity()).openFragment(new DashbordFragment());
            }
        });
    }

}
