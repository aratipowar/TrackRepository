package com.trackrepository.model;

import com.google.gson.annotations.SerializedName;

public class OwnerData {

    @SerializedName("html_url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
