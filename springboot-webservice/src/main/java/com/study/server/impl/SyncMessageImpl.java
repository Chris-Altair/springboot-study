package com.study.server.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.domain.DataNode;
import com.study.server.SyncMessage;
import com.study.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.jws.WebService;

@Component
@WebService(name="syncMessage",
        targetNamespace = "http://server.study.com",
        endpointInterface = "com.study.server.SyncMessage")
public class SyncMessageImpl implements SyncMessage {
    private static Logger logger = LoggerFactory.getLogger(SyncMessageImpl.class);
    @Override
    public void test() {
        System.out.println("webservice test success!");
    }

    @Override
    public String handleMessage(String msg, Integer code) {
        logger.debug("接收到消息：{}",msg);// ★使用{}占位符。避免字符串连接操作，减少String对象（不可变）带来的内存开销
        /*
         * 这里放消息处理的具体过程
         */
        DataNode node = new DataNode();
        node.setCode(code);
        node.setMsg(msg);
        logger.debug("消息处理成功！");
        try {
            String json = JsonUtil.toJson(node);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "消息处理失败";
        }
    }
}
