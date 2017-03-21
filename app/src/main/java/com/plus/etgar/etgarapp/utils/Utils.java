package com.plus.etgar.etgarapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.plus.etgar.etgarapp.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by davidgd on 02/26/2017.
 */

public class Utils {
    public static String PREF_NAME="name";
    public static String PREF_ADDRESS="address";
    public static   String PREF_PHONE="phone";
    public static String PREF_COMMENTS="comments";

    public static String convertDateToString(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

    public static void openPopupAlert(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.mange_app_dialog);
        TextView confirm= (TextView) dialog.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setTitle("ניהול אפליקציה");
        dialog.show();
    }

    public static String moneyFormat(double money){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(money);
        System.out.println(moneyString);

        return moneyString;
    }
}

