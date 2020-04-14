package com.joka.http.test;

import com.joka.http.OkHttpUtils;
import com.joka.http.log.LoggerInterceptor;
import okhttp3.OkHttpClient;
import org.junit.After;
import org.junit.Before;

/**
 * Created on 2020/4/14 5:41 下午.
 *
 * @author zhaozengjie
 * Description :
 */
public abstract class BaseTest {

    protected OkHttpClient okHttpClient = null;

    @Before
    public void before() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    @After
    public void after(){
        block();
    }

    protected void block(){
        // 异步线程，所以没执行完成就测试结束了，所以让当前线程休眠5s
        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
