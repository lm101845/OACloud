package org.xdq.demo.common.vo;



public class R<T> {
    public static final int CODE_OK = 200;
    public static final int CODE_ERR_BUSI = 500;
    public static final int CODE_ERR_SYS = 530;
    public static final int CODE_ERR_AUTHENTICATION = 520;//未认证（未登录）


    public static R<?> OK(){
        return new R<>(true,CODE_OK,null,null);
    }

    public static <T> R<T> OK(T data){
        return new R<T>(true,CODE_OK,null,data);
    }

    public static <T> R<T> OK(String message,T data){
        return new R<T>(true,CODE_OK,message,data);
    }

    public static <T> R<T> OK4msg(String message){
        return new R<T>(true,CODE_OK,message,null);
    }

    public static R<?> err(int errCode,String message){
        return new R<>(false,errCode,message,null);
    }

    public static <T> R<T> err(int errCode,String message,T data){
        return new R<T>(false,errCode,message,data);
    }


    private boolean success;

    private int code;//业务代码：200 成功、500 业务失败（用户操作失误导致的）、530 系统错误、520 未登录

    private String message;//业务操作结果概要描述

    private T data;//向前端发送的数据


    private R(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
