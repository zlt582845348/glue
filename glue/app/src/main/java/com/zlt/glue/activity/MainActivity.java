package com.zlt.glue.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zlt.framework.adapter.ZltBaseAdapter;
import com.zlt.framework.view.BaseActivity;
import com.zlt.glue.R;
import com.zlt.glue.viewfeatures.UserView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements UserView {
    private ZltBaseAdapter<String> adapter;
    private ListView listView;

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //git测试 方法嘎 分发给还是功夫还是
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//  网络请求
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("id","12");
//        map.put("name","北京市");
//        CoreManager.getHttp().get("http://123.206.61.198/index.php?r=member/homepage/city_id",map,"er", new IHttpCallback() {
//            @Override
//            public void success(String result, String tag) {
//                Log.d("","");
//            }
//
//            @Override
//            public void failed(String msg, String tag, Exception ex) {
//                Log.d("","");
//            }
//        });
        //图片加载
//        ImageView view = (ImageView)findViewById(R.id.imageView);
//        String url = "http://res.cngoldres.com/upload/2015/0515/556cc5edcb10bf415ba00d56b5eb19e2.jpg";
//        CoreManager.getImage().load(this,url,view);
        //json解析
//        HashMap<String,String> map = new HashMap();
//        map.put("asff","14123");
//        String json = CoreManager.getJson().getJson(map);
//        HashMap<String,String> m = CoreManager.getJson().getModel(json,new TypeToken<HashMap<String,String>>(){}.getType());
        //订阅发布
//        CoreManager.getRx().publish("1","2","3","4").bind(new IRx.IRxCallback() {
//            @Override
//            public void next(int type, Object obj) {
//                Log.v("","");
//            }
//        });

        //
        listView = (ListView)findViewById(R.id.listview);
        ArrayList<String> list = new ArrayList<>();
        list.add("扫码");
        list.add("RecyclerView");
        adapter = new ZltBaseAdapter<>(this,list,R.layout.item_list);
        adapter.setOnCreateItemListener(new ZltBaseAdapter.OnCreateItemListener() {

            @Override
            public void onCreateItem(int position, View convertView, ViewGroup parent, ZltBaseAdapter.ViewHolder holder) {
                TextView tv = holder.obtainView(convertView, R.id.text);
                String text = holder.getData();
                tv.setText(text);
            }
        });
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Intent intent0 = new Intent(MainActivity.this, QrCodeActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, RecyclerViewActivity.class);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void reflashView() {

    }
}
