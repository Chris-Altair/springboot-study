package com.study.client;

import com.study.server.SyncMessage;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * webservice客户端调用
 * 调用url:http://localhost:8080/syncMsg?msg=aaaa&&code=3
 * @Controller必须配合模版来使用，return没找到会404
 * @RestController是@ResponseBody和@Controller的组合注解
 */
@RestController
public class WebserviceClient {
    /*
        required=false 表示url中可以不穿入id参数,这时参数为默认defaultValue
     */
    @GetMapping("/syncMsg1")
    public String syncMsg1(@RequestParam(value = "msg",required = false,defaultValue = "aaa") String msg, @RequestParam(value = "code",required = false,defaultValue = "1") Integer code){
        // 接口地址
        String address = "http://127.0.0.1:8080/webservice/syncMessage?wsdl";
        // 代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // 设置代理地址
        jaxWsProxyFactoryBean.setAddress(address);
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(SyncMessage.class);
        // 创建一个代理接口实现
        SyncMessage sm = (SyncMessage) jaxWsProxyFactoryBean.create();
        // 数据准备
        // 调用代理接口的方法调用并返回结果
        String json = sm.handleMessage(msg,code);
        return json;
    }

    /**
     * 这种方式可以远程调用
     * @param msg
     * @param code
     * @return
     */
    @GetMapping("/syncMsg2")
    public String syncMsg2(@RequestParam(value = "msg",required = false,defaultValue = "aaa") String msg, @RequestParam(value = "code",required = false,defaultValue = "1") Integer code){
        // 接口地址
        String address = "http://127.0.0.1:8080/webservice/syncMessage?wsdl";

        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
        Client client = clientFactory.createClient(address);
        Object[] result = new Object[0];
//            result = client.invoke("test", new Object[]{"111","111"});
        try {
            result = client.invoke("handleMessage", new Object[]{msg,code});
            return (String)result[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result[0]);
        return "";
    }
}
