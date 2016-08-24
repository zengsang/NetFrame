package com.example.netframe.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**将要被映射的javabean
 * Created by 曾志强 on 2016/8/21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sennic {
    private Integer code;
    private String message;
    private List<SenniceData> data;

    public List<SenniceData> getData() {
        return data;
    }

    public void setData(List<SenniceData> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
