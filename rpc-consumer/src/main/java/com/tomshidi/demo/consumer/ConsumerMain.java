package com.tomshidi.demo.consumer;

import com.tomshidi.demo.interfaces.service.IDemoRmiService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tomshidi
 * @date 2022/6/14 18:14
 */
public class ConsumerMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("RmiClient.xml");
        IDemoRmiService demoRmiService = classPathXmlApplicationContext.getBean("rmiClient", IDemoRmiService.class);
        System.out.println("\"1\"+\"2\"计算结果是：" + demoRmiService.strConcat("1", "2"));
    }
}
