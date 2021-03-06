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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myapitesting.api.model.MovieModel;
import com.example.myapitesting.api.response.MovieResponse;
import com.example.myapitesting.utils.AppApi;
import com.example.myapitesting.utils.CustomProgressDialog;
import com.example.myapitesting.utils.PreferencesKeys;
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


public class ActMovieList extends AppCompatActivity {

    private  ListAdapter listAdapter;
    private ArrayList<MovieModel> arrayListAllMovieModel = new ArrayList<>();
    MovieResponse movieResponse;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    int intPage = 1;


    String TAG = "==ActMovieList==";

    @BindView(R.id.rlNoData)
    RelativeLayout rlNoData;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.materialRefreshLayout)
    MaterialRefreshLayout materialRefreshLayout;

    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_movielist);
        ButterKnife.bind(this);

        initialization();

        if (App.isInternetAvailWithMessage(tvTitle, ActMovieList.this)) {
            asyncGetMyMovies();
        }

    }


    //MyMovieList API
    public void asyncGetMyMovies() {
        try {
            if (customProgressDialog != null && !customProgressDialog.isShowing())
                customProgressDialog.show();

            Call callRetrofit2 = App.getRetrofitApiService().GetMovieList(AppApi.OP_POPULAR, App.APP_API_KEY, "" + intPage);
            callRetrofit2.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    try {

                        if (customProgressDialog != null)
                            customProgressDialog.dismiss();

                        App.setStopLoading(materialRefreshLayout);

                        movieResponse = response.body();

                        if (movieResponse == null) {
                            ResponseBody responseBody = response.errorBody();
                            if (responseBody != null) {
                                try {
                                    //App.showLog(TAG + "--OP_DASHBOARD-error-", " -/- " + responseBody.string());
                                    App.showLog(TAG + "-OP_POPULAR-/" + responseBody.string());
                                    rlNoData.setVisibility(View.VISIBLE);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            //200 sucess
                            App.showLogApiRespose(TAG + AppApi.OP_POPULAR, response);

                            if (movieResponse != null && movieResponse.arrayListMovieModel != null) {
                                if (movieResponse.arrayListMovieModel != null && movieResponse.arrayListMovieModel.size() > 0) {
                                    setAdapterData();
                                } else {
                                    materialRefreshLayout.setLoadMore(false);
                                    rlNoData.setVisibility(View.VISIBLE);
                                    if (movieResponse.total_pages != null) {
                                        App.showSnackBar(tvTitle, movieResponse.total_pages);
                                    } else {
                                        App.showSnackBar(tvTitle, strMsgSomethingWentWrong);
                                    }
                                }
                            } else {
                                App.showSnackBar(tvTitle, strMsgSomethingWentWrong);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        App.showSnackBar(tvTitle, strMsgSomethingWentWrong);
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    t.printStackTrace();
                    if (customProgressDialog != null)
                        customProgressDialog.dismiss();
                    App.showSnackBar(tvTitle, strMsgSomethingWentWrong);
                    App.setStopLoading(materialRefreshLayout);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (customProgressDialog != null)
                customProgressDialog.dismiss();
            App.showSnackBar(tvTitle, strMsgSomethingWentWrong);
            App.setStopLoading(materialRefreshLayout);

        }
    }


    private void setAdapterData() {
        try {

            //arrayListAllDashboardModel = StaticDataList.getStaticDashboardData();

            if (arrayListAllMovieModel == null || intPage <=1)
                arrayListAllMovieModel = new ArrayList<>();

            if (movieResponse != null && movieResponse.arrayListMovieModel != null && movieResponse.arrayListMovieModel.size() > 0) {
                arrayListAllMovieModel.addAll(movieResponse.arrayListMovieModel);
            } else
            {
                materialRefreshLayout.setLoadMore(false);

            }

            if(arrayListAllMovieModel != null && arrayListAllMovieModel.size() >0) {
                if (listAdapter == null) {
                    listAdapter = new ListAdapter(this, arrayListAllMovieModel);
                    recyclerView.setAdapter(listAdapter);
                } else {
                    listAdapter.notifyDataSetChanged();
                }
                rlNoData.setVisibility(View.GONE);
            }
            else
            {
                rlNoData.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.VersionViewHolder> {
        ArrayList<MovieModel> mArrayList;
        Context mContext;

        public ListAdapter(Context context, ArrayList<MovieModel> arrayListFollowers) {
            mArrayList = arrayListFollowers;
            mContext = context;
        }

        @Override
        public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_menulist, viewGroup, false);
            VersionViewHolder viewHolder = new VersionViewHolder(view);
            return viewHolder;
        }


        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {
            try {
                MovieModel mCasesListModel = mArrayList.get(i);

                versionViewHolder.tvMovieTitle.setText(StringUtils.setString(mCasesListModel.original_title));
                versionViewHolder.tvReleaseDate.setText(StringUtils.setString(mCasesListModel.release_date));
                versionViewHolder.tvOverView.setText(StringUtils.setString(mCasesListModel.overview));


                Picasso.with(mContext).load(StringUtils.setString(App.strImgBaseURL + mCasesListModel.poster_path)).fit().centerCrop().into(versionViewHolder.img);

                versionViewHolder.rlMenuItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{

                            Intent intent = new Intent(ActMovieList.this, ActMovieDetail.class);
                            intent.putExtra(AppFlags.tagFrom, "ActMovieList");
                            intent.putExtra(AppFlags.tagMovieListModel, mArrayList.get(i));
                            App.myStartActivity(ActMovieList.this, intent);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }


        class VersionViewHolder extends RecyclerView.ViewHolder {

            TextView tvMovieTitle, tvReleaseDate, tvOverView;
            ImageView img;

            RelativeLayout rlMenuItem;


            public VersionViewHolder(View itemView) {
                super(itemView);

                rlMenuItem = (RelativeLayout) itemView.findViewById(R.id.rlMenuItem);

                tvMovieTitle = (TextView) itemView.findViewById(R.id.tvMovieTitle);
                tvReleaseDate = (TextView) itemView.findViewById(R.id.tvReleaseDate);
                tvOverView = (TextView) itemView.findViewById(R.id.tvOverView);

                img = (ImageView) itemView.findViewById(R.id.img);


            }
        }


    }

    private void initialization() {

        App.sharePrefrences.setPref(PreferencesKeys.strLogin, "1");
        // for the listing
        materialRefreshLayout.setIsOverLay(true);
        materialRefreshLayout.setWaveShow(true);
        materialRefreshLayout.setWaveColor(0x55ffffff);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActMovieList.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                try {
                    //refreshing...
                    if (App.isInternetAvailWithMessage(tvTitle, ActMovieList.this)) {

                        intPage = 1;
                        arrayListAllMovieModel = new ArrayList<>();
                        asyncGetMyMovies();
                    } else {
                        App.setStopLoading(materialRefreshLayout);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                try {
                    if (App.isInternetAvailWithMessage(tvTitle, ActMovieList.this)) {

                        intPage = intPage + 1;
                        asyncGetMyMovies();
                    } else {
                        App.setStopLoading(materialRefreshLayout);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
