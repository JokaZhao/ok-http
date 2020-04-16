package com.joka.http.test;

import com.joka.http.OkHttpUtils;
import com.joka.http.callback.FileCallBack;
import okhttp3.Call;
import org.junit.Test;

import java.io.File;

/**
 * Created on 2020/4/16 3:32 下午.
 *
 * @author zhaozengjie
 * Description :
 */
public class DownloadFileTest extends BaseTest {

    @Test
    public void downloadFile() {
        String url = "";

        OkHttpUtils.get().url(url).build().execute(new FileCallBack(".", "aa.png") {
            @Override
            public void inProgress(float progress, long total, int id) {
                System.out.println(progress + "/" + total);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(File response, int id) {
                System.out.println(response.getAbsolutePath());
            }
        });

    }

}
