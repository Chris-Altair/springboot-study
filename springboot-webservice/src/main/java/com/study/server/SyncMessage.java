package com.study.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="syncMessage", targetNamespace="http://server.study.com")
public interface SyncMessage {
    @WebMethod //@WebMethod可以通过name指定webservice指定的方法名
    String handleMessage(@WebParam(name = "msg")String msg, @WebParam(name = "code")Integer code);//@WebParam可以通过name指定传递的参数名
    @WebMethod
    void test();
}
