package com.example.myapitesting.utils;

/**
 * Created by jeetendra.achtani on 07-02-2018.
 */

public class StringUtils {

    public static boolean isValidString(String string)
    {
        if(string !=null && string.trim().length() > 0)
            return true;
        else
            return false;
    }
    public static String setString(String string)
    {
        if(string !=null && string.trim().length() > 0)
            return string;
        else
            return "";
    }
}
