package com.plus.etgar.etgarapp.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by davidgd on 02/21/2017.
 */
@IgnoreExtraProperties
public class ProductBike implements Serializable {
    private String code;
    private String name;
    private String model;
    private float price;
    private int amount;
    private String client;
    private String size;
    private String color;
    private String description;
    private String image;
    private int type;
    private List<String> colors;
    private List<String> sizes;

    public ProductBike(){

    }
    public ProductBike(ProductBike productBike){
        model=productBike.getModel();
        code=(productBike.getCode());
        price=(productBike.getPrice());
        description=(productBike.getDescription());
        color=(productBike.getColor());
        image=(productBike.getImage());
        name=(productBike.getName());
        amount=1;
        client=(productBike.getClient());
        size=(productBike.getSize());
        type=(productBike.getType());
        colors=(productBike.getColors());
        sizes=(productBike.getSizes());


    }
    public ProductBike(String code, String name, String model, float price, int amount, String client, String size, String color, String description, String image, int type, List<String> colors, List<String> sizes) {
        this.code = code;
        this.name = name;
        this.model = model;
        this.price = price;
        this.amount = amount;
        this.client = client;
        this.size = size;
        this.color = color;
        this.description = description;
        this.image = image;
        this.type = type;
        this.colors = colors;
        this.sizes = sizes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    @Override
    public String toString() {
        return "ProductBike{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", client='" + client + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", type=" + type +
                ", colors=" + colors +
                ", sizes=" + sizes +
                '}';
    }
}
