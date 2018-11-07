package com.liushu.service;

import com.alibaba.fastjson.JSON;
import com.liushu.entity.MyMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BookFlowConsumer {
    private static String EXT = ":bookFlowRecord";
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "bookflow.queue")
    public void receiveQueue(String message) {
        MyMessage myMessage = JSON.parseObject(message,MyMessage.class);
        String userId = myMessage.getUserId();
        String bookFlowId = myMessage.getBookFlowId();
        String key = userId+EXT;
        if(redisTemplate.hasKey(key)){
            Long totalCount = redisTemplate.opsForList().size(key);
            if(totalCount <= 300){
                redisTemplate.opsForList().rightPush(key,bookFlowId);
            }else{
                redisTemplate.opsForList().leftPop(key);
                redisTemplate.opsForList().rightPush(key,bookFlowId);
            }
        }else{
            redisTemplate.opsForList().rightPush(key,bookFlowId);
        }
    }
}
