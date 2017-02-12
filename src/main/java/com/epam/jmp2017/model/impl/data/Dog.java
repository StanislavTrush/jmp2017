package com.epam.jmp2017.model.impl.data;

import com.epam.jmp2017.model.Data;
import com.epam.jmp2017.model.enums.Attribute;


public class Dog implements Data {
    private String name;
    private String color;

    public Dog(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int getTypeCode() {
        return 1;
    }
    @Override
    public String print() {
        return "Color of the dog called " + name + " is " + color + ".";
    }

    @Override
    public String get(Attribute attribute) {
        if(attribute != null) {
            switch (attribute) {
                case NAME:
                    return name;
                case COLOR:
                    return color;
            }
        }
        return null;
    }
}
