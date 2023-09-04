package com.trackrepository.model;

import com.google.gson.annotations.SerializedName;

public class AddOwnerData {
    @SerializedName("name")
    private String name;

    @SerializedName("login")
    private String login;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
