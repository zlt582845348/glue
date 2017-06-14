package com.zlt.glue.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zlt.framework.recycler.FullyGridLayoutManager;
import com.zlt.framework.recycler.FullyLinearLayoutManager;
import com.zlt.framework.recycler.DividerGridItemDecoration;
import com.zlt.framework.recycler.RecyclerAdapter;
import com.zlt.framework.recycler.RecyclerHolder;
import com.zlt.framework.reflash.SwipeRecyclerView;
import com.zlt.framework.view.BaseActivity;
import com.zlt.glue.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zlt
 * Date: 2016-07-12
 * Time: 14:32
 * RecyclerView实现
 */
public class RecyclerViewActivity extends BaseActivity {

    private SwipeRecyclerView swipeRecyclerView;
    private List<String> mDatas;
    private RecyclerAdapter mAdapter;
    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        swipeRecyclerView = (SwipeRecyclerView) findViewById(R.id.swipeRecyclerView);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
        mAdapter = new RecyclerAdapter(this,R.layout.item_recycler,mDatas);
        mAdapter.setOnItemCreateListener(new RecyclerAdapter.OnItemCreateListener() {
            @Override
            public void OnItemCreate(RecyclerHolder holder) {
                TextView tv = holder.getView(R.id.text);
                String s = holder.getData();
                tv.setText(s);
            }
        });
        mAdapter.setOnClickItemListener(new RecyclerAdapter.OnClickItemListener() {
            @Override
            public void onClickItemListener(View view, RecyclerHolder holder) {
                RecyclerViewActivity.this.showToast(holder.getData().toString());
            }
        });

        swipeRecyclerView.setModel(SwipeRecyclerView.MODEL_GRID);
        swipeRecyclerView.setAdapter(mAdapter);

        // 设置下拉刷新监听器
        swipeRecyclerView.setOnRefreshListener(new SwipeRecyclerView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRecyclerView.getRefreshLayout().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
                        // 更新完后调用该方法结束刷新
                        swipeRecyclerView.getRefreshLayout().setRefreshing(false);
                    }
                }, 2000);
            }
        });


        // 加载监听器
        swipeRecyclerView.setOnLoadMoreListener(new SwipeRecyclerView.OnLoadMoreListener() {
            int index = 0;
            @Override
            public void onLoad() {
                swipeRecyclerView.getRefreshLayout().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 加载完后调用该方法
                        if(index<3)
                        {
                            swipeRecyclerView.getRefreshLayout().setLoading(false);
                            index++;
                        }else{
                            //没有更多
                            swipeRecyclerView.getFooterView().setHaveMore(false);
                        }
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

}
