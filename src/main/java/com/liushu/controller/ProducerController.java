package com.liushu.controller;

import com.alibaba.fastjson.JSON;
import com.liushu.entity.MyMessage;
import com.liushu.service.BookFlowProducer;
import com.liushu.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProducerController {
    @Autowired
    private BookFlowProducer bookFlowProducer;

    /**
     * 接收消息
     * @param myMessage
     */
    @PostMapping(value = "v1/auth/producers")
    public void sendBookFlowLook(MyMessage myMessage, @CookieValue String liushuUserToken){
        String userId = CommonUtil.getUserId(liushuUserToken);
        myMessage.setUserId(userId);
        if(StringUtils.isBlank(myMessage.getBookFlowId())){
            return;
        }
        String jsonStr = JSON.toJSONString(myMessage);
        String destination = "bookflow.queue";
        bookFlowProducer.sendHasLook(destination, jsonStr);
    }
}

