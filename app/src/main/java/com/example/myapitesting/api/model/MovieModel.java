package com.example.myapitesting.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieModel implements Serializable {


    @SerializedName("title")
    public String title;

    @SerializedName("id")
    public String id;

    @SerializedName("release_date")
    public String release_date;

    @SerializedName("original_title")
    public String original_title;

    @SerializedName("overview")
    public String overview;

    @SerializedName("poster_path")
    public String poster_path;



}
