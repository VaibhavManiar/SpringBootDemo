package com.sample.springboot;

import com.sample.springboot.kafka.KafkaPulisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class KafkaProducerController {

    @Autowired
    private KafkaPulisher kafkaPulisher;

    @GetMapping("/send/{message}")
    public void send(@PathVariable("message") String message) {
        kafkaPulisher.sendMessage(message);
    }
}
