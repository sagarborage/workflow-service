package com.sowermate.tenantService.entities;

public enum DeptTypeEnum {
    OPTIMIZE("optimize"),
    Cutting("cutting"),
    DISPATCH("dispatch"),
    TOUGHEN("toughen");

    private final String stringValue;

    private DeptTypeEnum(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
