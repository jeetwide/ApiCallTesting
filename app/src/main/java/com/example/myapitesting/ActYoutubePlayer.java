package com.example.myapitesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapitesting.api.model.MovieModel;
import com.example.myapitesting.api.model.MovieVideoModel;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActYoutubePlayer extends YouTubeBaseActivity {


    String TAG = "ActYoutubePlayer";
    @BindView(R.id.btn)
    Button btn;

    @BindView(R.id.youtubtePlayerView)
    YouTubePlayerView youtubtePlayerView;

    YouTubePlayer.OnInitializedListener onInitializedListener;

    private MovieVideoModel movieVideoModel = null;
    private String strFrom = "";

    String key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_youtube_player);
        ButterKnife.bind(this);

        getIntentData();


        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(key);
               // youtubtePlayerView.initialize(PlayerConfig.API_KEY,onInitializedListener);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youtubtePlayerView.initialize(PlayerConfig.API_KEY,onInitializedListener);
        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    youtubtePlayerView.initialize(PlayerConfig.API_KEY,onInitializedListener);
            }
        });*/
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
                    if (bundle.getSerializable(AppFlags.tagMovieVideoModel) != null) {
                        movieVideoModel = (MovieVideoModel) bundle.getSerializable(AppFlags.tagMovieVideoModel);
                        key=movieVideoModel.key;
                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
