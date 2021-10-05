package com.example.websockets.controller;

import com.example.websockets.model.Greeting;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

  private SimpMessagingTemplate template;

  public GreetingController(SimpMessagingTemplate template) {
    this.template = template;
  }

//  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting2() throws InterruptedException {
    Thread.sleep(1000); // simulated delay
    return new Greeting("Hello, World!");
  }

  @GetMapping("/greeting")
  public String greeting() throws InterruptedException {
    return "greeting";
  }

  @Scheduled(fixedDelay = 2000)
  public void dispatch() {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//    headerAccessor.setSessionId("user");
    headerAccessor.setLeaveMutable(true);
    int value = (int) Math.round(Math.random() * 100d);
    template.convertAndSend("/topic/greetings", new Greeting(Integer.toString(value)));
//    template.convertAndSendToUser(
//        "user",
//        "/topic/greetings",
//        new Greeting(Integer.toString(value)),
//        headerAccessor.getMessageHeaders());
  }
}
