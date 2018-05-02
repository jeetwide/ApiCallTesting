package com.example.myapitesting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapitesting.api.model.MovieVideoModel;
import com.example.myapitesting.api.response.MovieVideoResponse;
import com.example.myapitesting.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActVideoList extends AppCompatActivity {


    String TAG = "ActVideoList";
    private MovieVideoResponse movieVideoResponse = null;
    private String strFrom = "";



    private ArrayList<MovieVideoModel> movieVideoModels = new ArrayList<>();

    @BindView(R.id.tvText)
    TextView tvText;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_video_list);
        ButterKnife.bind(this);
        getIntentData();



    }

    private void setAdapterData() {
        try {

            if (movieVideoResponse != null && movieVideoResponse.arrayListMovieVideomodel != null && movieVideoResponse.arrayListMovieVideomodel.size() > 0) {
                movieVideoModels = movieVideoResponse.arrayListMovieVideomodel;

             /*   for (MovieVideoModel model : movieVideoModels) {
                    App.showLog("==model==" + model.name);
                }
*/
            }



                    ListAdapter listAdapter = new ListAdapter(ActVideoList.this, movieVideoModels);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ActVideoList.this));
                    recyclerView.setAdapter(listAdapter);
                    recyclerView.setHasFixedSize(true);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
                    if (bundle.getSerializable(AppFlags.tagMovieVideoResponse) != null) {
                        movieVideoResponse = (MovieVideoResponse) bundle.getSerializable(AppFlags.tagMovieVideoResponse);

                        setAdapterData();
                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.VersionViewHolder> {
        ArrayList<MovieVideoModel> mArrayList;
        Context mContext;

        public ListAdapter(Context context, ArrayList<MovieVideoModel> arrayListFollowers) {
            mArrayList = arrayListFollowers;
            mContext = context;
        }

        @Override
        public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_member_list, viewGroup, false);
            VersionViewHolder viewHolder = new VersionViewHolder(view);
            return viewHolder;
        }


        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {
            try {
                MovieVideoModel mCasesListModel = mArrayList.get(i);


                versionViewHolder.tvName.setText(StringUtils.setString(mCasesListModel.name));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }


        class VersionViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvEmail, tvPhone, tvPinValue, tvPinTag;

            ImageView ivProfilepic;
            RelativeLayout rlRowMain;


            public VersionViewHolder(View itemView) {
                super(itemView);

                tvName = (TextView) itemView.findViewById(R.id.tvName);




            }

        }
    }


}

