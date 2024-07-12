package org.xdq.demo.common;

public enum LeaveStatusEnum {

    NOT_SUBMIT(0,"未提交"),
    SUBMITED(1,"已提交"),
    REJECTED(2,"已驳回"),
    PASS(3,"已批准"),
    REVERSE(4,"已销假");

    private int code;
    private String text;

    private LeaveStatusEnum(int code, String text){
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static String getText(int code){
        LeaveStatusEnum[] arr = LeaveStatusEnum.values();
        for(LeaveStatusEnum leaveStatusEnum : arr){
            if(code == leaveStatusEnum.code){
                return leaveStatusEnum.text;
            }
        }
        return null;
    }
}
