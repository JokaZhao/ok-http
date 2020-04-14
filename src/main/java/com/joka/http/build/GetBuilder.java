package com.joka.http.build;

import com.joka.http.call.GetRequest;
import com.joka.http.call.RequestCall;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created on 2020/4/13 5:44 下午.
 *
 * @author zhaozengjie
 * Description :
 */
@Slf4j
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable {

    @Override
    public RequestCall build() {
        if (params != null) {
            url = appendParams(url, params);
        }

        return new GetRequest(url, tag, params, headers, id).build();
    }

    protected String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }

        StringBuffer buffer = new StringBuffer();

        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (StringUtils.isEmpty(buffer.toString())) {
                buffer.append("?");
            } else {
                buffer.append("&");
            }
            buffer.append(entry.getKey()).append("=").append(entry.getValue());
        }
        String encode = null;
        try {
            encode = URLEncoder.encode(buffer.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("",e);
        }

        return url + encode;
    }


    @Override
    public GetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

}
