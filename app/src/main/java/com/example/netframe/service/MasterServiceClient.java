package com.example.netframe.service;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by 曾志强 on 2016/8/4.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MasterServiceClient {

    @RestService
    IMasterService mRestService;

    public IMasterService getmRestService() {
        return mRestService;
    }
/*    @Bean
    MasterRestErrorHandler mRestErrHandler;*/
}
