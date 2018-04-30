package com.example.myapitesting.api.response;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 11/15/2016.
 */

public class CommonResponse
{

    @SerializedName("total_results")
    public String total_results;

    @SerializedName("total_pages")
    public String total_pages;

}
