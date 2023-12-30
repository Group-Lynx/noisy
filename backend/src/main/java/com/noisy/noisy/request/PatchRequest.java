package com.noisy.noisy.request;

public class PatchRequest {
    private String new_name;
    private String new_pswd;
    private String auth_pswd;

    public String getNew_name() {
        return new_name;
    }

    public void setNew_name(String new_name) {
        this.new_name = new_name;
    }

    public String getNew_pswd() {
        return new_pswd;
    }

    public void setNew_pswd(String new_pswd) {
        this.new_pswd = new_pswd;
    }

    public String getAuth_pswd() {
        return auth_pswd;
    }

    public void setAuth_pswd(String auth_pswd) {
        this.auth_pswd = auth_pswd;
    }
}
