package com.example.websockets.notification;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationDispatcher {

//  private Set<String> listeners = new HashSet<>();
  private Map<String, StompHeaderAccessor> listeners = new HashMap<>();
  private SimpMessagingTemplate template;

//  public static String STOMP_DESTINATION = "/topic/messages.user.123";
  public static String STOMP_DEST = "/queue/messages";
  public static String STOMP_DESTINATION = STOMP_DEST + "-user";
  public static String STOMP_DESTINATION_SERVER = "/user/{user}" + STOMP_DEST;

  public NotificationDispatcher(SimpMessagingTemplate template) {
    this.template = template;
  }

//  public void add(String user) {
  public void add(String user, StompHeaderAccessor stompHeaderAccessor) {
//    listeners.add(user);
    listeners.put(user, stompHeaderAccessor);
  }

  public void remove(String user) {
    listeners.remove(user);
  }

  @SendToUser
  @Scheduled(fixedDelay = 2000)
  public void dispatch() {
    if (listeners.size() == 0) {
      return;
    }
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
    headerAccessor.setSessionId(listeners.get("john") != null ? listeners.get("john").getSessionId() : listeners.get("user").getSessionId());
    headerAccessor.setLeaveMutable(true);
    int value = (int) Math.round(Math.random() * 100d);

//    template.convertAndSend(STOMP_DESTINATION, new Greeting(Integer.toString(value)), headerAccessor.getMessageHeaders());
    template.convertAndSend(STOMP_DESTINATION_SERVER.replace("{user}", "user"), new Notification(Integer.toString(value)), headerAccessor.getMessageHeaders());
//    template.convertAndSendToUser("user", STOMP_DESTINATION, new Greeting(Integer.toString(value)), headerAccessor.getMessageHeaders());
//    template.convertAndSendToUser(
//        "user",
//        "/topic/greetings",
//        new Greeting(Integer.toString(value)),
//        headerAccessor.getMessageHeaders());
  }
}
