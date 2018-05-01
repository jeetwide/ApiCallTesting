package com.example.myapitesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapitesting.api.model.MovieModel;
import com.example.myapitesting.api.model.MovieVideoModel;
import com.example.myapitesting.api.response.MovieDetailsResponse;
import com.example.myapitesting.api.response.MovieResponse;
import com.example.myapitesting.api.response.MovieVideoResponse;
import com.example.myapitesting.utils.AppApi;
import com.example.myapitesting.utils.CustomProgressDialog;
import com.example.myapitesting.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapitesting.AppFlags.strMsgSomethingWentWrong;

public class ActMovieDetail extends AppCompatActivity {



    String TAG = "ActMovieDetail";


    private MovieModel movieModel = null;
    private String strFrom = "";


    MovieDetailsResponse movieDetailsResponse;
    MovieVideoResponse movieVideoResponse;

    private ArrayList<MovieVideoModel> arrayListMovieVideomodel = new ArrayList<>();


    @BindView(R.id.tvMovieTitle)
    TextView tvMovieTitle;

    @BindView(R.id.tvOverView)
    TextView tvOverView;

    @BindView(R.id.tvTest)
    TextView tvTest;

    @BindView(R.id.tvbudget)
    TextView tvbudget;

    @BindView(R.id.tvHomePage)
    TextView tvHomePage;

    @BindView(R.id.tvStatus)
    TextView tvStatus;

    @BindView(R.id.tvRuntime)
    TextView tvRuntime;

    @BindView(R.id.tvTagline)
    TextView tvTagline;

    @BindView(R.id.tvOverViewTAG)
    TextView tvOverViewTAG;

    @BindView(R.id.tvWatchVideo)
    TextView tvWatchVideo;

    @BindView(R.id.tvOtherDetailsTag)
    TextView tvOtherDetailsTag;

    @BindView(R.id.img)
    ImageView img;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_movie_detail);
        ButterKnife.bind(this);

        getIntentData();
        setViewData();
        setClickEvents();

        if (App.isInternetAvailWithMessage(tvMovieTitle, ActMovieDetail.this)) {
            asyncGetMovieDetails();
        }




    }

    private void setClickEvents() {
        tvWatchVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncGetMovieVideos();
            }
        });

    }

    private void getIntentData() {
        try {

            // common
            if (getIntent() != null) {
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    if (bundle.getString(AppFlags.tagFrom) != null) {
                        strFrom = bundle.getString(AppFlags.tagFrom);
                        App.showLog(TAG + "==strFrom==" + strFrom);
                    }


                    //difference
                    if (bundle.getSerializable(AppFlags.tagMovieListModel) != null) {
                        movieModel = (MovieModel) bundle.getSerializable(AppFlags.tagMovieListModel);
                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setViewData() {
        try {
            if (movieDetailsResponse != null) {

                tvMovieTitle.setText(StringUtils.setString(movieModel.original_title));
                tvOverView.setText(StringUtils.setString(movieModel.overview));
                tvHomePage.setText(StringUtils.setString("URL = "+movieDetailsResponse.homepage));
                tvbudget.setText(StringUtils.setString("Budget = "+movieDetailsResponse.budget));
                tvStatus.setText(StringUtils.setString("Released Status = "+movieDetailsResponse.status));
                tvRuntime.setText(StringUtils.setString("Runtime  = "+movieDetailsResponse.runtime+" minutes"));
                tvTagline.setText(StringUtils.setString("TagLine  = "+movieDetailsResponse.tagline));
                tvOverViewTAG.setText(StringUtils.setString("Overview"));
                tvOtherDetailsTag.setText(StringUtils.setString("Other Details"));

                Picasso.with(this).load(StringUtils.setString(App.strImgBaseURL+movieModel.poster_path)).fit().centerCrop().into(img);
            }
        } catch (Exception e) {
        }
    }
    //MyMovieDetails API
    public void asyncGetMovieDetails() {
        try {
            if (customProgressDialog != null && !customProgressDialog.isShowing())
                customProgressDialog.show();

            Call callRetrofit2 = App.getRetrofitApiService().GetMovieDetails(movieModel.id, App.APP_API_KEY );
            callRetrofit2.enqueue(new Callback<MovieDetailsResponse>() {
                @Override
                public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                    try {

                        if (customProgressDialog != null)
                            customProgressDialog.dismiss();

                        //App.setStopLoading(materialRefreshLayout);

                        movieDetailsResponse = response.body();

                        if (movieDetailsResponse == null) {
                            ResponseBody responseBody = response.errorBody();
                            if (responseBody != null) {
                                try {
                                    //App.showLog(TAG + "--OP_DASHBOARD-error-", " -/- " + responseBody.string());
                                    App.showLog(TAG + "-OP_MoviDetail-/" + responseBody.string());
                                   // rlNoData.setVisibility(View.VISIBLE);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            //200 sucess
                            App.showLogApiRespose(TAG + movieModel.id, response);

                            if (movieDetailsResponse != null) {
                                    setViewData();
                                }else {
                                App.showSnackBar(tvMovieTitle, strMsgSomethingWentWrong);
                            }
                            }
                        }
                     catch (Exception e) {
                        e.printStackTrace();
                        App.showSnackBar(tvMovieTitle, strMsgSomethingWentWrong);
                    }
                }

                @Override
                public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                    t.printStackTrace();
                    if (customProgressDialog != null)
                        customProgressDialog.dismiss();
                    App.showSnackBar(tvMovieTitle, strMsgSomethingWentWrong);
                   // App.setStopLoading(materialRefreshLayout);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (customProgressDialog != null)
                customProgressDialog.dismiss();
            App.showSnackBar(tvMovieTitle, strMsgSomethingWentWrong);
           // App.setStopLoading(materialRefreshLayout);

        }
    }

    //MyMovieVideos API
    public void asyncGetMovieVideos() {
        try {
            if (customProgressDialog != null && !customProgressDialog.isShowing())
                customProgressDialog.show();

            Call callRetrofit2 = App.getRetrofitApiService().GetVideoDetails(movieModel.id,"videos",App.APP_API_KEY);
            callRetrofit2.enqueue(new Callback<MovieVideoResponse>() {
                @Override
                public void onResponse(Call<MovieVideoResponse> call, Response<MovieVideoResponse> response) {
                    try {

                        if (customProgressDialog != null)
                            customProgressDialog.dismiss();

                        //App.setStopLoading(materialRefreshLayout);

                        movieVideoResponse = response.body();

                        if (movieVideoResponse == null) {
                            ResponseBody responseBody = response.errorBody();
                            if (responseBody != null) {
                                try {
                                    //App.showLog(TAG + "--OP_DASHBOARD-error-", " -/- " + responseBody.string());
                                    App.showLog(TAG + "-OP_MoviVideos-/" + responseBody.string());
                                    // rlNoData.setVisibility(View.VISIBLE);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            //200 sucess
                            App.showLogApiRespose(TAG + movieModel.id, response);

                            if (movieVideoResponse != null) {

                              //  tvWatchVideo.setText(movieVideoResponse.);
                                Toast.makeText(ActMovieDetail.this, "get message", Toast.LENGTH_SHORT).show();
                               // tvTest.setText(movieVideoResponse.arrayListMovieVideomodel.get(1).key);


                                for(int i=0;i<4;i++){

                                    Intent intent = new Intent(ActMovieDetail.this, ActYoutubePlayer.class);
                                    intent.putExtra(AppFlags.tagFrom, "ActMovieDetail");
                                    intent.putExtra(AppFlags.tagMovieVideoModel, movieVideoResponse.arrayListMovieVideomodel.get(i));
                                    App.myStartActivity(ActMovieDetail.this, intent);

                                }


                            }else {
                                App.showSnackBar(tvMovieTitle, strMsgSomethingWentWrong);
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        App.showSnackBar(tvMovieTitle, strMsgSomethingWentWrong);
                    }
                }

                @Override
                public void onFailure(Call<MovieVideoResponse> call, Throwable t) {
                    t.printStackTrace();
                    if (customProgressDialog != null)
                        customProgressDialog.dismiss();
                    App.showSnackBar(tvMovieTitle, strMsgSomethingWentWrong);
                    // App.setStopLoading(materialRefreshLayout);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (customProgressDialog != null)
                customProgressDialog.dismiss();
            App.showSnackBar(tvMovieTitle, strMsgSomethingWentWrong);
            // App.setStopLoading(materialRefreshLayout);

        }
    }

}
