package com.epam.jmp2017.model.json.impl.data;

import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.enums.Attribute;

public class Fridge implements DataModel {
    private int weight;
    private String brand;

    public Fridge(int weight, String brand) {
        this.weight = weight;
        this.brand = brand;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public int getTypeCode() {
        return 2;
    }
    public String print() {
        return "This fridge from " + brand + " weight " + weight + " kg.";
    }

    @Override
    public String get(Attribute attribute) {
        if(attribute != null) {
            switch (attribute) {
                case BRAND:
                    return brand;
            }
        }
        return null;
    }
}
