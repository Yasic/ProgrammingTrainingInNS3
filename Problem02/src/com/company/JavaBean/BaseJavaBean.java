package com.company.JavaBean;

/**
 * Created by yasic on 16-6-7.
 */
public class BaseJavaBean {
    private static final int BYTRSIZE = 100;
    protected byte[] serialize(){
        return new byte[BYTRSIZE];
    }
}
