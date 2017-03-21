package com.plus.etgar.etgarapp.models;

import java.util.List;

/**
 * Created by davidgd on 02/27/2017.
 */
public class ListProducts {
   private List<ProductBike> productBikeList;

    public ListProducts(){

    }
    public ListProducts(List<ProductBike> productBikeList) {
        this.productBikeList = productBikeList;
    }

    public List<ProductBike> getProductBikeList() {
        return productBikeList;
    }

    public void setProductBikeList(List<ProductBike> productBikeList) {
        this.productBikeList = productBikeList;
    }
}
