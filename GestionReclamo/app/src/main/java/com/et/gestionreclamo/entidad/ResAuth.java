package com.et.gestionreclamo.entidad;

import java.io.Serializable;

public class ResAuth implements Serializable {
    int code;
    String msg;
    String jwtToken;

    public ResAuth() {
    }

    public ResAuth(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public ResAuth(int code, String msg, String jwtToken) {
        this.code = code;
        this.msg = msg;
        this.jwtToken = jwtToken;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
