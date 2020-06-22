package com.sample.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDemoApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private HelloWorldController controller;

    @Autowired
    private KafkaProducerController kafkaProducerController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test()
    void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(restTemplate).isNotNull();
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/hello/Vaibhav", String.class).contains("Hello Vaibhav"));
    }

    @Test()
    void sendMessage() {
        assertThat(kafkaProducerController).isNotNull();
        assertThat(restTemplate).isNotNull();
        restTemplate.getForEntity("http://localhost:" + port + "/message/send/Hi", String.class);
    }
}
