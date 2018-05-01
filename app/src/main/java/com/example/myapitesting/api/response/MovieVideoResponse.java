package com.example.myapitesting.api.response;


import com.example.myapitesting.api.model.MovieModel;
import com.example.myapitesting.api.model.MovieVideoModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Elite-D35 on 4/19/2018.
 */

public class MovieVideoResponse extends CommonResponse {

    @SerializedName("results")
    public ArrayList<MovieVideoModel> arrayListMovieVideomodel;
}
