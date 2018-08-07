package com.example.data.bean;

/*****************************   
 * @作者：chenk
 * @描述：${用于接收服务器返回数据的basebean}
 ******************************/

public class ResultBo<T> {

    public T data;
    public String message = "";
    public String errorCode = "";
    public String success = "0";

    @Override
    public String toString() {
        return "ResultBo{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", success=" + success +
                '}';
    }
}

