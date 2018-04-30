package com.example.myapitesting;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.example.myapitesting.api.ApiService;
import com.example.myapitesting.api.response.CommonResponse;
import com.example.myapitesting.utils.AppApi;
import com.example.myapitesting.utils.PreferencesKeys;
import com.example.myapitesting.utils.SharePrefrences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {




    //Test
    public static String strBaseHostUrl = "https://api.themoviedb.org/3/";

    // class for the share pref keys and valyes get set
    public static SharePrefrences sharePrefrences;

    // share pref name
    //public String PREF_NAME = "halacab_driver_app";
    public static String PREF_NAME = "testing_app";


    private static App mInstance;

    static Context mContext;

    public static String APP_USERTYPE = "0"; // user_type = 0 for customer/traveller & user_type = 1 for driver
    public static String APP_MODE = "2"; // app_mode 1 = Live   2=testing
    public static String APP_API_KEY= "7e8f60e325cd06e164799af1e317d7a7"; // app_mode 1 = Live   2=testing
    public static String APP_PLATFORM = "2"; // platform = 2   passing app platefrom  2 for android


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        try{
            mContext = getApplicationContext();
            sharePrefrences = new SharePrefrences(App.this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static boolean isInternetAvailWithMessage(View view, Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }

        App.showSnackBar(view, AppFlags.strMsgNetError);
        return false;
    }

    public static void showLog(String strMessage) {
        Log.v("==App==", "--strMessage--" + strMessage);
    }

    public static void showSnackBar(View view, String strMessage) {
        try {
            Snackbar snackbar = Snackbar.make(view, strMessage, Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.BLACK);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static ApiService getRetrofitApiService() {
        return getRetrofitBuilder().create(ApiService.class);
    }


    public static void showLogApi(String strMessage) {
        //Log.v("==App==", "--strMessage--" + strMessage);
        System.out.println("--API-MESSAGE--" + strMessage);

        //  appendLogApi("c_api", strMessage);
    }

    public static void showLogApiRespose(String op, Response response) {
        //Log.w("=op==>" + op, "response==>");
        String strResponse = new Gson().toJson(response.body());
        Log.d("=op==>" + op, "response==>" + strResponse);
        // appendLogApi(op + "_r_api", strResponse);
    }

    public static void setStopLoading(MaterialRefreshLayout materialRefreshLayout) {
        try {
            materialRefreshLayout.finishRefresh();
            // load more refresh complete
            materialRefreshLayout.finishRefreshLoadMore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Retrofit getRetrofitBuilder() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        return new Retrofit.Builder()
                .baseUrl(App.strBaseHostUrl)
                .client(getClient()) // it's optional for adding client
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static OkHttpClient getClient() {
        //OkHttpClient client =

        return new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();
        //return client;
    }

    public  static HashMap<String, RequestBody> addCommonHashmap()
    {
        HashMap<String, RequestBody> commonHashMap = new HashMap<>();

       RequestBody r_api_key = App.createPartFromString(App.APP_API_KEY);



        commonHashMap.put("api_key", r_api_key);

        return commonHashMap;
    }

    public static RequestBody createPartFromString(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }



}
