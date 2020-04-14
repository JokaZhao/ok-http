package com.joka.http.test;

import com.joka.http.OkHttpUtils;
import com.joka.http.callback.StringCallback;
import com.joka.http.log.LoggerInterceptor;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;

/**
 * Created on 2020/4/14 5:14 下午.
 *
 * @author zhaozengjie
 * Description :
 */
public class PostTest extends BaseTest{


    @Test
    public void getReq() {
        String url = "http://www.csdn.net/";
        OkHttpUtils.post().url(url)
                .addParams("userName", "222")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println("resp :" + response);
            }
        });

    }
}
