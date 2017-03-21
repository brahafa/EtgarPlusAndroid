package com.plus.etgar.etgarapp.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plus.etgar.etgarapp.MainActivity;
import com.plus.etgar.etgarapp.R;
import com.plus.etgar.etgarapp.adapters.AddProductRVAdapter;
import com.plus.etgar.etgarapp.adapters.OrderRVAdapter;
import com.plus.etgar.etgarapp.logic.ProductLogic;
import com.plus.etgar.etgarapp.models.ProductBike;
import com.plus.etgar.etgarapp.utils.SharePref;
import com.plus.etgar.etgarapp.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.google.android.gms.internal.zzt.TAG;

public class DashbordFragment extends Fragment {

    private View view;
     private TextView date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashbord, container, false);
        initUI();
        initAllData();
        initRV();
        return view;

    }

    private void initAllData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("array");
        Log.d("TAG", " myRef.getKey() = [" +  myRef.getKey() + "]");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProductLogic.getInstance().removeProductsList();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ProductLogic.getInstance().addProductsList(dataSnapshot.child(postSnapshot.getKey()).getValue(ProductBike.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void initRV() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        initCalendarDate();

        List<ProductBike> bikeList= ProductLogic.getInstance().getmOrderProductsList();
            //"http://www.etgarplus.co.il/wp-content/uploads/2017/01/MT450-27.5.jpg"
            //http://www.etgarplus.co.il/wp-content/uploads/2016/08/CBHACK1.jpg  exessorise
            //http://www.etgarplus.co.il/wp-content/uploads/2012/12/%D7%AA%D7%9E%D7%95%D7%A0%D7%9411.png


        OrderRVAdapter mAdapter = new OrderRVAdapter(bikeList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

     private void initCalendarDate() {
         date = (TextView) view.findViewById(R.id.date);
         date.setText(Utils.convertDateToString(new Date()));
         date.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.N)
             @Override
             public void onClick(View v) {
                openChooserDatePopup();
             }
         });
     }

     @RequiresApi(api = Build.VERSION_CODES.N)
     private void openChooserDatePopup() {
         Calendar newCalendar = Calendar.getInstance();
         DatePickerDialog datePickerDialog = new DatePickerDialog(getContext() , android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new DatePickerDialog.OnDateSetListener() {

             public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                 Calendar newDate = Calendar.getInstance();
                 newDate.set(year, monthOfYear, dayOfMonth);
                 date.setText(Utils.convertDateToString((newDate.getTime())));
             }

         },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

         datePickerDialog.show();

     }


     private void initUI() {
        ImageView user_icon= (ImageView) view.findViewById(R.id.user_icon);
        TextView addProduct = (TextView) view.findViewById(R.id.addProduct);
        TextView send_order = (TextView) view.findViewById(R.id.send_order);
         if(ProductLogic.getInstance().isManager()){
             addProduct.setText("ערוך מוצרים");
         }
         user_icon.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ((MainActivity)getActivity()).openFragment(new ProfileFragment());
             }
         });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openFragment(new AddProductFragment());
            }
        });
        send_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getOrderText());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }

     private String getOrderText() {
         String orderMsg;
         List<ProductBike> productBikes = ProductLogic.getInstance().getmOrderProductsList();
         orderMsg= getDetailseFroPref() +"\n";
         for (int i=0; i< productBikes.size(); i++){

             orderMsg+= "פריט מס' " +  String.valueOf(i+1) + "\n";
             orderMsg+= addProductTextToOrder(productBikes.get(i));
         }
         return orderMsg;
     }

    private String addProductTextToOrder(ProductBike productBike){
        String orderMsg="";
        orderMsg+= "קוד מוצר " +productBike.getCode() + "\n";
        orderMsg+= productBike.getName() +" " +  productBike.getModel()+ "\n";
        if(!productBike.getColor().equals("בחר צבע")){
            orderMsg+= "צבע " +productBike.getColor() + "\n";
        }
        if(!productBike.getSize().equals("בחר מידה")){
            orderMsg+= "מידה " +productBike.getSize() + "\n";
        }
        orderMsg+= "כמות " +productBike.getAmount() + "\n";
        orderMsg+= "מחיר " + Utils.moneyFormat(productBike.getPrice()) + "\n  \n";


        return orderMsg;
    }

    private String getDetailseFroPref() {
         String returnString="";
         SharePref sharePref = SharePref.getInstance(getContext());
         if(!TextUtils.isEmpty(sharePref.getData(Utils.PREF_NAME))){
             returnString+=" שם: "+sharePref.getData(Utils.PREF_NAME);
             returnString+="\n";
         }
         if(!TextUtils.isEmpty(sharePref.getData(Utils.PREF_ADDRESS))){
             returnString+=" כתובת: "+sharePref.getData(Utils.PREF_ADDRESS);
             returnString+="\n";
         }
         if(!TextUtils.isEmpty(sharePref.getData(Utils.PREF_PHONE))){
             returnString+=" טלפון: "+sharePref.getData(Utils.PREF_PHONE);
             returnString+="\n";
         }
         if(!TextUtils.isEmpty(sharePref.getData(Utils.PREF_COMMENTS))){
             returnString+=" הערות: "+sharePref.getData(Utils.PREF_COMMENTS);
             returnString+="\n";
         }
         return returnString;
     }


 }
