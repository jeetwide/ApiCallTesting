package com.example.myapitesting;

import com.example.myapitesting.api.model.MovieModel;

import java.util.ArrayList;


public class AppFlags
{
    // for the Myprofile when update it's profile data
    public static boolean isEditProfile = false;
    public static boolean isEditProfileBase = false;

    public static String tagFrom = "tagFrom";
    public static String tagMobileNo = "tagMobileNo";
    public static String tagTitle = "tagTitle";
    public static String tagUrl = "tagUrl";


    public static int INT_INVALID_TOKEN = 401;
    public static String STR_INVALID_TOKEN = "401";


    public static String strMsgNetError = "We can't detect an internet connection. please check and try again.";
    public static String strMsgServerError = "We can't detect an internet connection. please check and try again.";
    public static String strMsgSomethingWentWrong = "Oops, something went wrong. Please try again later.";

    //Passing models
    public static String tagProfileModel = "mProfileModel";


    //Passing
    public static String tagSaveNewCallTestDataModel= "SaveNewCallTestDataModel";
    public static String tagHistoryModel= "HistoryModel";







    //public static ArrayList<CompaniesListModel> arrayListCompaniesListModel;
   // public static ArrayList<PolicyListModel> arrayListPolicyListModel;

   // public static NewCallTestModel newCallTestModel;

    private ArrayList<MovieModel> arrayListMovieModel = new ArrayList<>();

    public static String tagMovieListModel = "MovieListModel";




}
