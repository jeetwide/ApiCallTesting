package com.example.myapitesting.api.model;

import com.google.gson.annotations.SerializedName;

public class MovieModel {


    @SerializedName("title")
    public String title;

    @SerializedName("id")
    public String id;

    @SerializedName("release_date")
    public String release_date;
}
