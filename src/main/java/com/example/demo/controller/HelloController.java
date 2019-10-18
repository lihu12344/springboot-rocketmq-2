package com.example.demo.controller;

import com.example.demo.producer.ProducerService;
import com.example.demo.producer.TransactionProducerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    private ProducerService producerService;

    @Resource
    private TransactionProducerService transactionProducerService;

    @RequestMapping("/send")
    public String sendSync(){
        producerService.sendSync();

        return "success";
    }

    @RequestMapping("/sendAsync")
    public String hello2(){
        producerService.sendAsync();

        return "success 2";
    }

    @RequestMapping("/sendOneway")
    public String hello3(){
        producerService.sendOneWay();

        return "success 3";
    }

    @RequestMapping("/sendDelay")
    public String hello4(){
        producerService.sendDelay();

        return "success 4";
    }

    @RequestMapping("/sendInorder")
    public String hello5(){
        producerService.sendInOrder();

        return "success 5";
    }

    @RequestMapping("/sendInTransaction")
    public String hello6(){
        transactionProducerService.sendInTransaction();

        return "success 6";
    }
}
