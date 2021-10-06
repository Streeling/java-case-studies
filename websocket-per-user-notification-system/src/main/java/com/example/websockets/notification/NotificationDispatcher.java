package com.example.websockets.notification;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Component
public class NotificationDispatcher {

  public static String DESTINATION_BASE = "/queue/notifications";
  public static String DESTINATION_CLIENT = DESTINATION_BASE + "-user";
  public static String DESTINATION_SERVER = "/user/{user}" + DESTINATION_BASE;

  private SimpMessagingTemplate messagingTemplate;
  private SessionRegistry sessionRegistry;
  private boolean scheduledDispatchToAll = true;

  public NotificationDispatcher(SimpMessagingTemplate messagingTemplate, SessionRegistry sessionRegistry) {
    this.messagingTemplate = messagingTemplate;
    this.sessionRegistry = sessionRegistry;
  }

  @Scheduled(fixedDelay = 2000)
  public void dispatchToAll() {
    if (scheduledDispatchToAll) {
      for (Object principal : sessionRegistry.getAllPrincipals()) {
        User user = (User) principal;
        int value = (int) Math.round(Math.random() * 100d);
        messagingTemplate.convertAndSendToUser(user.getUsername(), DESTINATION_BASE, new Notification("all-" + Integer.toString(value)));
      }
    }
  }

  public void dispatchToUser(String username) {
    int value = (int) Math.round(Math.random() * 100d);
//    messagingTemplate.convertAndSend(DESTINATION_SERVER.replace("{user}", "user"), new Notification(username + "-" + Integer.toString(value)));
    messagingTemplate.convertAndSendToUser(username, DESTINATION_BASE, new Notification(username + "-" + Integer.toString(value)));
  }

  public void dispatchToUserSession(String username, String sessionId) {
    int value = (int) Math.round(Math.random() * 100d);
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
    headerAccessor.setSessionId(sessionId);
    headerAccessor.setLeaveMutable(true);
//    messagingTemplate.convertAndSend(DESTINATION_SERVER.replace("{user}", "user"),
//                                     new Notification(username + "(" + sessionId + ") -" + Integer.toString(value)), headerAccessor.getMessageHeaders());
    messagingTemplate.convertAndSendToUser(username, DESTINATION_BASE, new Notification(username + "(" + sessionId + ") -" + Integer.toString(value)),
                                           headerAccessor.getMessageHeaders());
  }

  public void setScheduledDispatchToAll(boolean scheduledDispatchToAll) {
    this.scheduledDispatchToAll = scheduledDispatchToAll;
  }
}
