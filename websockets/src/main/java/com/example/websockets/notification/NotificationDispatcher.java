package com.example.websockets.notification;

import com.example.websockets.model.Greeting;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class NotificationDispatcher {
  private Set<String> listeners = new HashSet<>();
  private SimpMessagingTemplate template;

  public NotificationDispatcher(SimpMessagingTemplate template) {
    this.template = template;
  }

  public void add(String user) {
    listeners.add(user);
  }

  public void remove(String user) {
    listeners.remove(user);
  }

  @Scheduled(fixedDelay = 2000)
  public void dispatch() {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//    headerAccessor.setSessionId("user");
    headerAccessor.setLeaveMutable(true);
    int value = (int) Math.round(Math.random() * 100d);
    template.convertAndSend("/topic/messages.user.123", new Greeting(Integer.toString(value)));
//    template.convertAndSendToUser(
//        "user",
//        "/topic/greetings",
//        new Greeting(Integer.toString(value)),
//        headerAccessor.getMessageHeaders());
  }
}
