package com.zlt.framework.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 通用适配器
 *
 * @author 张玲涛
 *
 * 2015-4-19 下午5:25:25
 */
public class ZltBaseAdapter<T> extends BaseAdapter {

	private OnCreateItemListener listener;
	private ArrayList<T> items = new ArrayList<>();
	private Context context;
	private int resId;

	public ZltBaseAdapter(Context context, ArrayList<T> data, int resId) {
		if(data==null)
		{
			data = new ArrayList<T>();
		}
		items = data;
		this.context = context;
		this.resId = resId;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return items.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	public ArrayList<T> getItems()
	{
		return items;
	}
	/**
	 *更新数据
	 */
	public void updata(ArrayList<T> gridItem)
	{
		this.items = gridItem;
		this.notifyDataSetChanged();
	}
	/**
	 * 添加数据
	 */
	public void add(T listData)
	{
		this.items.add(listData);
		this.notifyDataSetChanged();
	}
	/**
	 * 添加数据
	 */
	public void add(ArrayList<T> listItem)
	{
		this.items.addAll(listItem);
		this.notifyDataSetChanged();
	}
	/**
	 * 按索引位置删除
	 * @param index
	 */
	public void delete(int index)
	{
		this.items.remove(index);
		this.notifyDataSetChanged();
	}

	/**
	 * 删除指定对象
	 * @param data
	 */
	public void delete(Object data)
	{
		this.items.remove(data);
		this.notifyDataSetChanged();
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resId, null);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//holder中data是否为空都需要设置 兼容数据变化 list刷新
		holder.setData(items.get(position));
		if(listener!=null)
		{
			listener.onCreateItem(position, convertView, parent, holder);
		}
		return convertView;
	}

	/**
	 * 各个控件的缓存
	 */
	public static class ViewHolder{
		private SparseArray<View> views = new SparseArray<View>();
		//
		private Object data;
		//
		private Object tag;
		/**
		 * 指定resId和类型即可获取到相应的view
		 * @param convertView
		 * @param resId
		 * @param <V>
		 * @return
		 */
		public <V extends View> V obtainView(View convertView, int resId){
			View v = views.get(resId);
			if(null == v){
				v = convertView.findViewById(resId);
				views.put(resId, v);
			}
			return (V)v;
		}

		public void setData(Object data)
		{
			this.data = data;
		}

		public <O extends Object> O getData()
		{
			return (O)data;
		}

		public void setTag(Object tag)
		{
			this.tag = tag;
		}

		public <O extends Object> O getTag()
		{
			return (O)tag;
		}
	}

	public void setOnCreateItemListener(OnCreateItemListener listener)
	{
		this.listener = listener;
	}

	public interface OnCreateItemListener{
		public void onCreateItem(int position, View convertView, ViewGroup parent, ViewHolder holder);
	}
}
