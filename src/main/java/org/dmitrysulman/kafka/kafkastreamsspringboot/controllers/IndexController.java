package org.dmitrysulman.kafka.kafkastreamsspringboot.controllers;

import org.dmitrysulman.kafka.kafkastreamsspringboot.kafka.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class IndexController {

    private final Sender sender;

    @Autowired
    public IndexController(Sender sender) {
        this.sender = sender;
    }

    @GetMapping("/{text}")
    @ResponseBody
    public String index(@PathVariable String text) {
        sender.send(text, "stream-topic1");
        return "Sent " + text;
    }
}
