package org.xdq.demo.common;

/**
 * 基础信息状态枚举
 */
public enum InfoStatusEnum {

    undetermined(0,"未确定"),
    confirmed(1,"已确定"),
    enabled(2,"已启用"),
    disabled(3,"已禁用");


    private Integer code;
    private String text;

    private InfoStatusEnum(Integer code,String text){
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static String getTextByCode(Integer code){
        InfoStatusEnum[] values = InfoStatusEnum.values();
        for(InfoStatusEnum status : values){
            if (status.equals(code)) {

                return status.text;
            }
        }

        return null;

    }
}
