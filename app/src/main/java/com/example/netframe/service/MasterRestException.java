package com.example.netframe.service;

import org.springframework.http.HttpStatus;

/**
 * Created by wxy on 2015/10/23.
 */
public class MasterRestException extends RuntimeException {

    protected HttpStatus mStatusCode;

    public MasterRestException(String detailMessage) {
        super(detailMessage);
        mStatusCode = null;
    }
    public MasterRestException(HttpStatus statusCode, String detailMessage) {
        super(detailMessage);
        this.mStatusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.mStatusCode = mStatusCode;
    }
}
