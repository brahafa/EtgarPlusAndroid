package com.plus.etgar.etgarapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.plus.etgar.etgarapp.MainActivity;
import com.plus.etgar.etgarapp.R;
import com.plus.etgar.etgarapp.adapters.AddProductRVAdapter;
import com.plus.etgar.etgarapp.logic.ProductLogic;
import com.plus.etgar.etgarapp.models.ListProducts;
import com.plus.etgar.etgarapp.models.ProductBike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.internal.zzt.TAG;

public class AddProductPagerFragment extends Fragment implements AddProductRVAdapter.AdapterCallback {
    // Store instance variables
    private String title;
    private int page;
    private View view;
    private List<ProductBike> productList;

    @Override
    public void onItemChoose(int position) {
        ((MainActivity)getActivity()).clearBackStack();
        ((MainActivity)getActivity()).openFragment(new DashbordFragment());
    }

    // newInstance constructor for creating fragment with arguments
    public static AddProductPagerFragment newInstance(int page, String title) {
        AddProductPagerFragment fragmentFirst = new AddProductPagerFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_add_product_pager, container, false);
        productList= new ArrayList<>();
        initListToDisplay();
        initRV();
        //addDataToDatabase();
        return view;
    }

    private void initListToDisplay() {
        for(int i=0; i< ProductLogic.getInstance().getAllProductsList().size(); i++){
            if(ProductLogic.getInstance().getAllProductsList().get(i).getType()==page){
                productList.add(ProductLogic.getInstance().getAllProductsList().get(i));
            }
        }
    }

    private void initRV() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        AddProductRVAdapter mAdapter = new AddProductRVAdapter(productList, getContext(), new AddProductRVAdapter.AdapterCallback() {
            @Override
            public void onItemChoose(int index) {

                if(ProductLogic.getInstance().isManager()) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("param1", productList.get(index));
                    NewProductFragment newPFragment = new NewProductFragment();
                    newPFragment.setArguments(bundle);
                    ((MainActivity) getActivity()).clearBackStack();
                    ((MainActivity) getActivity()).openFragment(newPFragment);
                }else{
                    ((MainActivity)getActivity()).clearBackStack();
                     ((MainActivity)getActivity()).openFragment(new DashbordFragment());
                }



            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }
}
