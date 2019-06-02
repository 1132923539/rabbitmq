package net.canway.rabbitmq.spring_integretion.config;

import net.canway.rabbitmq.spring_integretion.pojo.User;

public class MyMessageDelegate {

    public void handleMessage(User messageBody){

        System.out.println(messageBody.getName());
        System.out.println(messageBody.getAge());
    }
}
