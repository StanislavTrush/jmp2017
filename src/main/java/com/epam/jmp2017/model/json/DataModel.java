package com.epam.jmp2017.model.json;

import com.epam.jmp2017.model.enums.Attribute;

//sOlid
public interface DataModel
{
    int getTypeCode();
    String print();
    String get(Attribute attribute);
}
