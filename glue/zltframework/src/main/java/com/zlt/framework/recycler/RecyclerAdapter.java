package com.zlt.framework.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zlt
 * Date: 2016-07-12
 * Time: 14:46
 * RecyclerView适配器
 */
public class RecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private int layoutId;
    private List item_data;
    private OnItemCreateListener onItemCreateListener;
    private OnClickItemListener onClickItemListener;

    public RecyclerAdapter(Context context,int layoutId,List item_data)
    {
        this.context = context;
        this.layoutId = layoutId;
        if(item_data==null)
        {
            item_data = new ArrayList();
        }
        this.item_data = item_data;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(layoutId, null,false);
        RecyclerHolder holder = new RecyclerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerHolder h = (RecyclerHolder)holder;
        if(onItemCreateListener!=null)
        {
            h.setData(item_data.size() > position ? item_data.get(position) : null);
            onItemCreateListener.OnItemCreate(h);

        }
        if(onClickItemListener!=null&&h.itemView!=null)
        {
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onClickItemListener(v,h);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return item_data.size();
    }

    public <O extends Object> O getItemData(int index)
    {
        O o = null;
        if(item_data!=null&&item_data.size()>index)
        {
            o = (O)item_data.get(index);
        }
        return o;
    }
    /**
     *更新数据
     */
    public void updata(ArrayList gridItem)
    {
        item_data = gridItem;
        this.notifyDataSetChanged();
    }
    /**
     * 添加数据
     */
    public void add(Object listData)
    {
        item_data.add(listData);
        this.notifyDataSetChanged();
    }
    /**
     * 添加数据
     */
    public void add(ArrayList listItem)
    {
        item_data.addAll(listItem);
        this.notifyDataSetChanged();
    }
    /**
     * 按索引位置删除
     * @param index
     */
    public void delete(int index)
    {
        item_data.remove(index);
        this.notifyDataSetChanged();
    }

    /**
     * 删除指定对象
     * @param data
     */
    public void delete(Object data)
    {
        item_data.remove(data);
        this.notifyDataSetChanged();
    }
    public interface OnItemCreateListener{
        public void OnItemCreate(RecyclerHolder holder);
    }

    public void setOnItemCreateListener(OnItemCreateListener listener)
    {
        this.onItemCreateListener = listener;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener)
    {
        this.onClickItemListener = onClickItemListener;

    }

    public interface OnClickItemListener{
        public void onClickItemListener(View view, RecyclerHolder holder);
    }
}
