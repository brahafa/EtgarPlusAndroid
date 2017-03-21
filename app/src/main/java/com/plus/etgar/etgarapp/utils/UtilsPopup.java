package com.plus.etgar.etgarapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by davidgd on 03/02/2017.
 */

public class UtilsPopup {

    public static void DialogChooseFromList(final Context context, final CharSequence[] listItems, final View view, final ChooseItemDialog chooseItemDialog) {
        if(listItems!=null)
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("")
                        .setItems(listItems, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                chooseItemDialog.onItemChoose(listItems[which].toString(), which);
                            }
                        });
                builder.show();
            }
        });

    }
    public interface ChooseItemDialog {
        void onItemChoose(String returnChoose, int index);
    }

    public static CharSequence[] getAmountList() {
        CharSequence[] charSequencesAmount= new CharSequence[50];
        for(int i=0; i<50; i++){
            charSequencesAmount[i]=String.valueOf(i);
        }
        return charSequencesAmount;
    }

}
