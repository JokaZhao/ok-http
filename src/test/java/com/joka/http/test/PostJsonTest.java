package com.joka.http.test;

import com.alibaba.fastjson.JSONObject;
import com.joka.http.OkHttpUtils;
import com.joka.http.callback.StringCallback;
import okhttp3.Call;
import okhttp3.MediaType;
import org.junit.Test;

/**
 * Created on 2020/4/14 5:29 下午.
 *
 * @author zhaozengjie
 * Description :
 */
public class PostJsonTest extends BaseTest{

    @Test
    public void postJsonTest(){
        String url = "http://www.csdn.net/";
        JSONObject req = new JSONObject();
        req.put("userName","test");

        OkHttpUtils.postString()
                .url(url)
                .content(req.toJSONString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println(response);
                    }
                });
    }

}
