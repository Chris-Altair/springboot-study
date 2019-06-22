package com.study;

import com.study.callback.CallbackSender;
import com.study.fanout.FanoutSender;
import com.study.topic.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    private FanoutSender fanoutSender;
    @Autowired
    private TopicSender topicSender;
    @Autowired
    private CallbackSender callbackSender;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSenders(){
        fanoutSender.send(1);
        topicSender.send("so sad","lay.rabbit.a");
        callbackSender.send("callback : happy");
    }


}
