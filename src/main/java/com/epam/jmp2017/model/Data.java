package com.epam.jmp2017.model;

import com.epam.jmp2017.model.enums.Attribute;

//sOlid
public interface Data
{
    int getTypeCode();
    String print();
    String get(Attribute attribute);
}
