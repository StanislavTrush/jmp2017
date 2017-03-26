package com.epam.jmp2017.model.json;

import com.epam.jmp2017.model.enums.Attribute;

import javax.enterprise.inject.Model;

//sOlid
@Model
public interface DataModel
{
    int getTypeCode();
    String print();
    String get(Attribute attribute);
}
