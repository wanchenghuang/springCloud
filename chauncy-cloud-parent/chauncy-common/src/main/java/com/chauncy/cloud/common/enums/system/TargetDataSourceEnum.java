package com.chauncy.cloud.common.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author cheng
 * @create 2020-03-06 13:08
 */
public enum TargetDataSourceEnum {

    MASTER("master"),SLAVE1("slave1");

    private String value;

    TargetDataSourceEnum(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}
