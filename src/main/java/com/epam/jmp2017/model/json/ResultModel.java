package com.epam.jmp2017.model.json;

import javax.enterprise.inject.Model;

@Model
public class ResultModel {
    private int typeCode;
    private String result;

    public int getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    public ResultModel(int typeCode, String result) {
        this.typeCode = typeCode;
        this.result = result;
    }
}
