package com.noisy.noisy.request;
import java.io.Serializable;

public class SignupRequest implements Serializable{
    private String name;
    private String pswd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

}
