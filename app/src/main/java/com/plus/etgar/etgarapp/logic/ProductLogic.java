package com.plus.etgar.etgarapp.logic;

import com.plus.etgar.etgarapp.models.ProductBike;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidgd on 02/22/2017.
 */
public class ProductLogic {

    public enum ProductType {BIKE, ACCESSORIES, SPARE_PARTS }

    private List<ProductBike> allProductsList;
    private List<ProductBike> mOrderProductsList;
    private boolean isManager=false;

    private static ProductLogic ourInstance = new ProductLogic();

    public static ProductLogic getInstance() {
        return ourInstance;
    }

    public void setAllProductsList(List<ProductBike> allProductsList){
        if(this.allProductsList==null){
            this.allProductsList= new ArrayList<>();
        }
        this.allProductsList=allProductsList;
    }
    public List<ProductBike> getAllProductsList(){
        if(this.allProductsList==null){
            this.allProductsList= new ArrayList<>();
        }
        return allProductsList;
    }

    public void addProductsList(ProductBike productBike){
        if(this.allProductsList==null){
            this.allProductsList= new ArrayList<>();
        }
        this.allProductsList.add(productBike);
    }
    public void removeProductsList(){
        if(this.allProductsList==null){
            this.allProductsList= new ArrayList<>();
        }
        this.allProductsList.clear();
    }

    public void addOrderProductsToList(ProductBike productBike){
        if(mOrderProductsList==null){
            mOrderProductsList=new ArrayList<>();
        }
        mOrderProductsList.add(new ProductBike(productBike));
    }

    public List<ProductBike> getmOrderProductsList(){
        if(mOrderProductsList==null){
            mOrderProductsList=new ArrayList<>();
        }
        return mOrderProductsList;
    }

    private void removeProductFromOrderList(int index){
        if(mOrderProductsList!=null) {
            mOrderProductsList.remove(index);
        }

    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }
}
