package com.joka.http.build;

import com.joka.http.OkHttpUtils;
import com.joka.http.call.OtherRequest;
import com.joka.http.call.RequestCall;


public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
