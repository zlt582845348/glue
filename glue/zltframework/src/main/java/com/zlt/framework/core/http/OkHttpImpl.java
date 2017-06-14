package com.zlt.framework.core.http;

import com.zlt.framework.core.featrues.IHttp;
import com.zlt.framework.core.featrues.IHttpCallback;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/21.
 */

public class OkHttpImpl implements IHttp {

    @Override
    public void get(String url, final IHttpCallback callback) {
        request("get",url,null,"",callback);
    }

    @Override
    public void get(String url, Object tag, IHttpCallback callback) {
        request("get",url,null,tag,callback);
    }

    @Override
    public void get(String url, HashMap<String, Object> params, IHttpCallback callback) {
        request("get",url,params,"",callback);
    }

    @Override
    public void get(String url, HashMap<String, Object> params, Object tag, IHttpCallback callback) {
        request("get",url,params,tag,callback);
    }

    @Override
    public void post(String url, IHttpCallback callback) {
        request("post",url,null,"",callback);
    }

    @Override
    public void post(String url, Object tag, IHttpCallback callback) {
        request("post",url,null,tag,callback);
    }

    @Override
    public void post(String url, HashMap<String, Object> params, IHttpCallback callback) {
        request("post",url,params,"",callback);
    }

    @Override
    public void post(String url, HashMap<String, Object> params, Object tag, IHttpCallback callback) {
        request("post",url,params,tag,callback);
    }

    @Override
    public void putFile(String path, String url, IHttpCallback callback) {

    }

    @Override
    public void putFile(File file, String url, IHttpCallback callback) {

    }

    private static OkHttpClient client;
    private OkHttpClient getClient()
    {
        //创建okHttpClient对象
        if(client==null)
        {
            client = new OkHttpClient();
        }
        return client;
    }

    private void request(String type, String url, HashMap<String, Object> params, Object tag, final IHttpCallback callback)
    {
        //创建一个Request
        Request.Builder builder = new Request.Builder();

        if(type.equals("get")&&params!=null&&params.size()>0)
        {
            url += "?";
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = String.valueOf(entry.getKey());
                String val = String.valueOf(entry.getValue());
                url += key + "=" + val + "&";
            }
            url = url.substring(0,url.length()-1);
        }else if(type.equals("post")&&params!=null&&params.size()>0)
        {
            FormBody.Builder fb = new FormBody.Builder();
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = String.valueOf(entry.getKey());
                String val = String.valueOf(entry.getValue());
                fb.add(key,val);
            }
            RequestBody formBody = fb.build();
            builder.post(formBody);
        }

        final Request request = builder.url(url).tag(tag).build();
        //new call
        Call call = getClient().newCall(request);
        //请求加入调度
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
                if(callback!=null)
                {
                    callback.failed(e.getMessage(),"",e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(callback!=null)
                {
                    String result = response.body().string();
                    String tag = String.valueOf(response.request().tag());
                    callback.success(result,tag);
                }
            }
        });
    }
}
