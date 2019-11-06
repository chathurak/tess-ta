package com.languagematters.tessta;

import com.languagematters.tessta.controller.HelloWorldController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TessTaApplicationTests {

    @Autowired
    private HelloWorldController helloWorldController;

    @Test
    public void contextLoads() {
        assertThat(helloWorldController).isNotNull();
    }

}
