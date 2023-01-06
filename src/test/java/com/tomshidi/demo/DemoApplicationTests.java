package com.tomshidi.demo;

import com.tomshidi.demo.config.ThirdPartConfig;
//import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

    @Autowired
    private ThirdPartConfig thirdPartConfig;

//    @Test
//    void contextLoads() {
//    }

}
