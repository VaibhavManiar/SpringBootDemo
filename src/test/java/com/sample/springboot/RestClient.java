package com.sample.springboot;

import org.springframework.web.client.RestTemplate;

public class RestClient {

    public static RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
