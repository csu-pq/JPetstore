package org.csu.mypetstore.utils;

public class ResultFactory {
    private int status;
    private String msg;
    private Object data;

    public ResultFactory() {
    }

    public ResultFactory(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() { return status; }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultFactory successResult(Object data,String msg) {
        return new ResultFactory(200, msg, data);
    }

    public static ResultFactory failedResult(int code, String msg) {
        return new ResultFactory(code, msg, null);
    }
}
