package com.liushu.service;

import com.alibaba.fastjson.JSON;
import com.liushu.entity.MyMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "mytest.queue")
    public void receiveQueue(String message) {
        MyMessage myMessage = JSON.parseObject(message,MyMessage.class);
        System.out.println("Consumer收到的报文为:"+myMessage.getUserId()+","+myMessage.getBookFlowId());
    }
}
