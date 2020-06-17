package com.et.gestionreclamo.entidad;

import java.io.Serializable;

public class Usuario implements Serializable {
    String username;
    String password;
    String tokenPublic;

    public Usuario() {

    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.tokenPublic = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFTkRFIFRFQ05PTE9HSUFTIFNFQyIsImlhdCI6MTU2OTg3NDk4OCwiZXhwIjoxNjAxNDExMDYwLCJhdWQiOiJldC5ibyIsInN1YiI6IkVOREUgVEVDTk9MT0dJQVMiLCJzZXJ2aWNpbyI6IkFHQyJ9.C_-0Ll337IKduXQB-wHgjzVymCoRXlRUg-gU-oR2wbc";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
