package com.example.myapitesting.api.response;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Elite-D35 on 4/19/2018.
 */

public class MovieDetailsResponse extends CommonResponse {



    @SerializedName("budget")
    public String budget;

    @SerializedName("homepage")
    public String homepage;

    @SerializedName("status")
    public String status;

    @SerializedName("tagline")
    public String tagline;

    @SerializedName("runtime")
    public String runtime;

}
