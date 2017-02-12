package com.epam.jmp2017.model;

public class Result {
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

    public Result(int typeCode, String result) {
        this.typeCode = typeCode;
        this.result = result;
    }
}
