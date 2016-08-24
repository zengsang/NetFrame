package com.example.netframe.service;

/**用于访问网络，包括post和get请求
 * Created by 曾志强 on 2016/8/21.
 */
/**
 * Created by 曾志强 on 2016/8/4.
 */

import com.example.netframe.bean.Sennic;
import com.example.netframe.utils.Constant;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientHeaders;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.springframework.http.client.OkHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**HOST 表示访问的url头部
 * @author CQB
 */
@Rest(rootUrl = Constant.HOST,
        converters = {FormHttpMessageConverter.class,
                MappingJackson2HttpMessageConverter.class
        },
        requestFactory = OkHttpRequestFactory.class)
public interface IMasterService extends RestClientRootUrl, RestClientHeaders, RestClientErrorHandling {
@Get("/interest/findInterestlist")
Sennic getSennic();
}

