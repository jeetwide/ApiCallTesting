package com.example.myapitesting.api.response;


import com.example.myapitesting.api.model.MovieModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Elite-D35 on 4/19/2018.
 */

public class MovieResponse extends CommonResponse {

    @SerializedName("results")
    public ArrayList<MovieModel> arrayListMovieModel;
}
