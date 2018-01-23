package com.example.zy.googlerecyclerviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<String> data;
    private boolean isRefresh = false;//是否刷新中
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle("Android");
        loadBackdrop();
        init();


        //设置SwipeRefreshLayout
        //设置进度条的颜色主题，最多能设置四种 加载颜色是循环播放的，只要没有完成刷新就会一直循环，holo_blue_bright>holo_green_light>holo_orange_light>holo_red_light
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //设置下拉刷新的监听
        swipeRefreshLayout.setOnRefreshListener(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(data);
        mRecyclerView.setAdapter(mAdapter);


    }

    /*
     * 初始化  设置ListView
     */
    private void init() {
        data = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            data.add("这是第" + i + "个数据");
        }
    }

    private void loadBackdrop() {
        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(backdrop);
    }

    /*
     * 监听器SwipeRefreshLayout.OnRefreshListener中的方法，当下拉刷新后触发
     */
    @Override
    public void onRefresh() {
        //检查是否处于刷新状态
        if (!isRefresh) {
            isRefresh = true;
            //模拟加载网络数据，这里设置4秒，正好能看到4色进度条
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    //显示或隐藏刷新进度条
                    swipeRefreshLayout.setRefreshing(false);
                    //修改adapter的数据
                    data.add("这是新添加的数据");
                    mAdapter.notifyDataSetChanged();
                    isRefresh = false;
                }
            }, 4000);
        }
    }
}
