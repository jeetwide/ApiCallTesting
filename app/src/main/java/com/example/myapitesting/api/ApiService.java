package com.example.myapitesting.api;



import com.example.myapitesting.api.response.CommonResponse;
import com.example.myapitesting.api.response.MovieDetailsResponse;
import com.example.myapitesting.api.response.MovieResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {








    @GET("movie/{op}")
    Call<MovieResponse> GetMovieList(
            @Path("op") String op,
            @Query("api_key") String api_key,
            @Query("page") String page
    );

    @GET("movie/{op}")
    Call<MovieDetailsResponse> GetMovieDetails(
            @Path("op") String op,
            @Query("api_key") String api_key
           //@Query("language") String language
    );

    @GET("web-servicetest.php")
    Call<CommonResponse> setUserLogout(
            @Query("op") String op,
            @Query("uid") String uid,

            @Query("platform") String platform,
            @Query("app_mode") String app_mode,
            @Query("user_type") String user_type,
            @Query("accessToken") String accessToken
    );




















}