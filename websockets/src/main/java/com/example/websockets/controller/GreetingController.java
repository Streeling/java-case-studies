package com.example.websockets.controller;

import com.example.websockets.notification.NotificationDispatcher;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

import static com.example.websockets.notification.NotificationDispatcher.STOMP_DESTINATION;

@Controller
public class GreetingController {

  private SimpMessagingTemplate template;
  private NotificationDispatcher notificationDispatcher;
  private SessionRegistry sessionRegistry;

  public GreetingController(SimpMessagingTemplate template, NotificationDispatcher notificationDispatcher, SessionRegistry sessionRegistry) {
    this.template = template;
    this.notificationDispatcher = notificationDispatcher;
    this.sessionRegistry = sessionRegistry;
  }

  @GetMapping("/greeting")
  public String greeting(Model model) throws InterruptedException {
    model.addAttribute("stompDestination", STOMP_DESTINATION);
    return "greeting";
  }

  @GetMapping("/anonymous")
  public String anonymous() throws InterruptedException {
    return "anonymous";
  }


  @MessageMapping("/start")
  public void start(StompHeaderAccessor stompHeaderAccessor, Principal principal) {
//    notificationDispatcher.add(stompHeaderAccessor.getSessionId());
    notificationDispatcher.add(principal.getName(), stompHeaderAccessor);
  }
  @MessageMapping("/stop")
  public void stop(StompHeaderAccessor stompHeaderAccessor, Principal principal) {
//    notificationDispatcher.remove(stompHeaderAccessor.getSessionId());
    notificationDispatcher.remove(principal.getName());
  }
}
