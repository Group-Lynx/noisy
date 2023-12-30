package com.noisy.noisy.utils;

public class ErrorResponse {
    String err;
    String msg;
    public ErrorResponse(String err,String msg){
        this.err=err;
        this.msg=msg;
    }
    public String getErr(){return this.err;}
    public String getMsg(){return this.msg;}
    public void setErr(String err){this.err=err;}
    public void setMsg(String msg){this.msg=msg;}
}
