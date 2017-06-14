package com.zlt.framework.reflash;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zlt.framework.recycler.DividerGridItemDecoration;
import com.zlt.framework.recycler.DividerItemDecoration;
import com.zlt.framework.util.ViewUtil;

/**
 * Created by Administrator on 2017/6/6.
 */

public class SwipeRecyclerView extends RelativeLayout {

    public static final int MODEL_LINEAR = 1;
    public static final int MODEL_GRID = 2;

    private Context context;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private IRefreshView footerView;
    private OnLoadMoreListener onLoadMoreListener;

    public SwipeRecyclerView(Context context) {
        super(context);
        this.context = context;
        initView();
        setColor();
        setFirstRefresh(true);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        setColor();
        setFirstRefresh(true);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        setColor();
        setFirstRefresh(true);
    }

    private void initView()
    {
        footerView = RefreshViewManager.getFooter(context);
        ((View)footerView).setId(ViewUtil.getViewId());
        LayoutParams layoutParams_footer = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams_footer.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView((View)footerView,layoutParams_footer);
        ((View) footerView).setVisibility(GONE);

        refreshLayout = new RefreshLayout(context);
        LayoutParams layoutParams_refresh = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams_refresh.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams_refresh.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(refreshLayout,layoutParams_refresh);
        refreshLayout.setOnLoadListener(onLoadListener);

        recyclerView = new RecyclerView(context);
        LayoutParams layoutParams_recycler = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        refreshLayout.addView(recyclerView,layoutParams_recycler);

    }

    private void setColor()
    {
        refreshLayout.setColorSchemeColors(Color.RED, Color.GRAY,
                Color.GREEN, Color.BLUE);
    }

    private void setFirstRefresh(boolean refresh)
    {
        if(refresh)
        {
            // 这句话是为了，第一次进入页面的时候显示加载进度条
            refreshLayout.autoRefresh();
        }
    }

    public void setModel(int model)
    {
        switch (model)
        {
            case MODEL_LINEAR:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                setLayoutManager(linearLayoutManager);
                addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL_LIST));
                break;
            case MODEL_GRID:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context,4);
                setLayoutManager(gridLayoutManager);
                addItemDecoration(new DividerGridItemDecoration(context));
                break;
            default:
                LinearLayoutManager linearLayoutManager_ = new LinearLayoutManager(context);
                setLayoutManager(linearLayoutManager_);
                addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL_LIST));
                break;
        }
    }

    private RefreshLayout.OnLoadListener onLoadListener = new RefreshLayout.OnLoadListener() {
        @Override
        public void onLoad(boolean isLoading) {
            if(isLoading)
            {
                if(footerView.haveMore())
                {
                    ((View) footerView).setVisibility(VISIBLE);
                    LayoutParams layoutParams_refresh = (LayoutParams)refreshLayout.getLayoutParams();
                    layoutParams_refresh.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    layoutParams_refresh.addRule(RelativeLayout.ABOVE,((View)footerView).getId());
                    recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                    if(onLoadMoreListener!=null)
                    {
                        onLoadMoreListener.onLoad();
                    }
                    footerView.drag();
                }
            }else
            {
                if(footerView.haveMore())
                {
                    ((View) footerView).setVisibility(GONE);
                    LayoutParams layoutParams_refresh = (LayoutParams)refreshLayout.getLayoutParams();
                    layoutParams_refresh.removeRule(RelativeLayout.ABOVE);
                    layoutParams_refresh.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    footerView.end();
                }
            }
        }
    };

    public void setLayoutManager(RecyclerView.LayoutManager layout){
        recyclerView.setLayoutManager(layout);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        recyclerView.addItemDecoration(itemDecoration);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener)
    {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{
        public void onLoad();
    }

    public interface OnRefreshListener{
        public void onRefresh();
    }

    public void setOnRefreshListener(final OnRefreshListener onRefreshListener){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(onRefreshListener!=null)
                {
                    onRefreshListener.onRefresh();
                }
                //刷新时取消底部没有更多的状态
                if(!footerView.haveMore()){
                    footerView.setHaveMore(true);
                }
            }
        });
    }

    public RefreshLayout getRefreshLayout()
    {
        return refreshLayout;
    }

    public IRefreshView getFooterView()
    {
        return footerView;
    }
}
