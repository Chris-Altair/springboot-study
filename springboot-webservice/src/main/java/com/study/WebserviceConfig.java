package com.study;

import com.study.server.SyncMessage;
import org.apache.cxf.Bus;

import javax.xml.ws.Endpoint;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class WebserviceConfig {

    @Autowired
    private Bus bus;
    @Autowired
    private SyncMessage syncMessage;

    /**
     * 注入servlet  bean name不能dispatcherServlet 否则会覆盖dispatcherServlet
     * @return
     */
    @Bean(name = "cxfServlet")
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(),"/webservice/*");
    }

    /**
     * 注册syncMessage接口到webservice服务
     * @return
     */
    @Bean(name = "WebServiceEndpoint")
    public Endpoint sweptPayEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, syncMessage);
        endpoint.publish("/syncMessage");
        return endpoint;
    }
}
