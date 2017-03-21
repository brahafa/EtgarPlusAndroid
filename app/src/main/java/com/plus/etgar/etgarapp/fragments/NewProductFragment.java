package com.plus.etgar.etgarapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.plus.etgar.etgarapp.MainActivity;
import com.plus.etgar.etgarapp.R;
import com.plus.etgar.etgarapp.models.ProductBike;
import com.plus.etgar.etgarapp.utils.UtilsPopup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String PRODUCT_BIKE = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ProductBike productToUpdate;
    private String mParam2;
    private View view;
    private EditText code, name, model,url,colors, sizes, price, details, type;
    private TextView add_product, remove_product;
    private int typeNumber=-1;

    public  NewProductFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productToUpdate = (ProductBike) getArguments().getSerializable(PRODUCT_BIKE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_new_product, container, false);
        initUi();
        return view;
    }

    private void initUi() {
        code= (EditText) view.findViewById(R.id.code);
        type= (EditText) view.findViewById(R.id.type);
        name= (EditText) view.findViewById(R.id.name);
        model= (EditText) view.findViewById(R.id.model);
        url= (EditText) view.findViewById(R.id.url);
        colors= (EditText) view.findViewById(R.id.colors);
        sizes= (EditText) view.findViewById(R.id.sizes);
        price= (EditText) view.findViewById(R.id.price);
        details= (EditText) view.findViewById(R.id.details);
        remove_product= (TextView) view.findViewById(R.id.remove_product);
        add_product= (TextView) view.findViewById(R.id.add_product);

        initDataFiled();
    }

    private void initDataFiled() {

        code.setText(productToUpdate.getCode());
        name.setText(productToUpdate.getName());
        model.setText(productToUpdate.getModel());
        url.setText(productToUpdate.getImage());
        colors.setText(getColors());
        sizes.setText(getSizes());
        price.setText(productToUpdate.getPrice()+"");
        details.setText(productToUpdate.getDescription());
        type.setText(getTypeStr());
        final CharSequence[] typeTitle= {"אופניים" , "אביזרים" ,"חלפים"};
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsPopup.DialogChooseFromList(getContext(), typeTitle, type, new UtilsPopup.ChooseItemDialog() {
                    @Override
                    public void onItemChoose(String returnChoose, int index) {
                        type.setText(returnChoose);
                        typeNumber=index;
                    }
                });
            }
        });

        remove_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeProduct(code.getText().toString());
            }
        });

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrUpdateProduct();
                openDashbord();
            }
        });

    }

    private String getTypeStr() {
        switch(productToUpdate.getType()){
            case 0:
                return "אופניים";
            case 1:
                return "אביזרים";
            case 2:
                return "חלפים";
            default:
                return "אופניים";
        }

    }

    private void openDashbord() {
        ((MainActivity)getActivity()).clearBackStack();
        ((MainActivity)getActivity()).openFragment(new DashbordFragment());
    }

    private String getSizes() {
        List<String> listSize= productToUpdate.getSizes();
        String sizesToReturn="";
        if(listSize==null){
            return "";
        }
        for(int i=0; i<listSize.size(); i++){
            sizesToReturn+=listSize.get(i)+" , ";
        }
        return sizesToReturn;
    }
    private String getColors() {
        List<String> listColor= productToUpdate.getColors();
        if(listColor==null){
            return "";
        }
        String sizesToReturn="";
        for(int i=0; i<listColor.size(); i++){
            sizesToReturn+=listColor.get(i)+" , ";
        }
        return sizesToReturn;
    }

    private Object getProductToServer() {
        ProductBike productBike =new ProductBike();
        productBike.setCode(code.getText().toString());
        productBike.setModel(model.getText().toString());
        productBike.setName(name.getText().toString());
        productBike.setImage(url.getText().toString());
        productBike.setDescription(details.getText().toString());
        productBike.setPrice(Float.valueOf(price.getText().toString()));
        productBike.setCode(code.getText().toString());
        productBike.setColors(convertArrayToList(colors));
        productBike.setSizes(convertArrayToList(sizes));
        if(typeNumber!=-1){
            productBike.setType(typeNumber);
        }else{
            productBike.setType(productToUpdate.getType());
        }

        return productBike;
    }

    private List<String> convertArrayToList(EditText editText) {
        if(!editText.getText().toString().equals("")){
            editText.getText().toString().split(",");
            List<String> stringList = new ArrayList<String>(Arrays.asList(editText.getText().toString().split(","))); //new ArrayList is only needed if you absolutely need an ArrayList
            return  stringList;
        }
        return null;
    }

    private void addOrUpdateProduct() {
        removeProduct(code.getText().toString());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("array");

        myRef.push().setValue(getProductToServer());
    }
    private void removeProduct(final String code) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("array");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if(code.equals(dataSnapshot.child(postSnapshot.getKey()).getValue(ProductBike.class).getCode())){
                        ref.child(postSnapshot.getKey()).removeValue();
                    }
                }
                openDashbord();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
