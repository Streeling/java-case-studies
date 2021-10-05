package com.example.websockets.controller;

import com.example.websockets.model.Greeting;
import com.example.websockets.notification.NotificationDispatcher;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class GreetingController {

  private SimpMessagingTemplate template;
  private NotificationDispatcher notificationDispatcher;

  public GreetingController(SimpMessagingTemplate template, NotificationDispatcher notificationDispatcher) {
    this.template = template;
    this.notificationDispatcher = notificationDispatcher;
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

  @GetMapping("/anonymous")
  public String anonymous() throws InterruptedException {
    return "anonymous";
  }


  @MessageMapping("/start")
  public void start(StompHeaderAccessor stompHeaderAccessor, Principal principal) {
//    notificationDispatcher.add(stompHeaderAccessor.getSessionId());
    notificationDispatcher.add(principal.getName());
  }
  @MessageMapping("/stop")
  public void stop(StompHeaderAccessor stompHeaderAccessor, Principal principal) {
//    notificationDispatcher.remove(stompHeaderAccessor.getSessionId());
    notificationDispatcher.remove(principal.getName());
  }
}
