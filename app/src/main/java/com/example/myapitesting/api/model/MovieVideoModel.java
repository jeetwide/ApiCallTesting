package com.example.myapitesting.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieVideoModel implements Serializable {


    @SerializedName("type")
    public String type;

    @SerializedName("site")
    public String site;

    @SerializedName("key")
    public String key;

    @SerializedName("name")
    public String name;





}
