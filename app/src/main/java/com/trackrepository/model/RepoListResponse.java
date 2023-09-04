package com.trackrepository.model;

import com.google.gson.annotations.SerializedName;

public class RepoListResponse {

    @SerializedName("name")
    private String name;

    @SerializedName("html_url")
    private String url;

    @SerializedName("description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
