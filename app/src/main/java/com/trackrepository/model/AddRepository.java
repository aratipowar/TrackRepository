package com.trackrepository.model;

import com.google.gson.annotations.SerializedName;

public class AddRepository
{
    @SerializedName("name")
    private String name;

    @SerializedName("owner")
    private AddOwnerData addOwnerData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddOwnerData getAddOwnerData() {
        return addOwnerData;
    }

    public void setAddOwnerData(AddOwnerData addOwnerData) {
        this.addOwnerData = addOwnerData;
    }
}
