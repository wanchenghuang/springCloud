package com.chauncy.cloud.common.enums.system;

/**
 * @Author cheng
 * @create 2020-03-07 22:43
 */
public enum TargetDataSourceEnum {

    MASTER("mysql"),SLAVE1("db2");

    private String value;

    TargetDataSourceEnum(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
