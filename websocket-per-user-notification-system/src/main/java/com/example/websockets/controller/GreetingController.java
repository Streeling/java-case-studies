package com.example.websockets.controller;

import com.example.websockets.notification.NotificationDispatcher;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.websockets.notification.NotificationDispatcher.DESTINATION_CLIENT;

@Controller
public class GreetingController {

  private NotificationDispatcher notificationDispatcher;
  private SessionRegistry sessionRegistry;

  public GreetingController(NotificationDispatcher notificationDispatcher, SessionRegistry sessionRegistry) {
    this.notificationDispatcher = notificationDispatcher;
    this.sessionRegistry = sessionRegistry;
  }

  @GetMapping("/greeting")
  public String greeting(Model model) throws InterruptedException {
    model.addAttribute("stompDestination", DESTINATION_CLIENT);
    return "greeting";
  }

  @GetMapping("/anonymous")
  public String anonymous() throws InterruptedException {
    return "anonymous";
  }

  @GetMapping("/disable-scheduled-dispatch-to-all")
  public @ResponseBody void disableDispatchToAll() {
    notificationDispatcher.setScheduledDispatchToAll(false);
  }

  @GetMapping("/enable-scheduled-dispatch-to-all")
  public @ResponseBody void enableDispatchToAll() {
    notificationDispatcher.setScheduledDispatchToAll(true);
  }

  @GetMapping("/dispatch-to-user")
  public @ResponseBody void dispatchToUser(@RequestParam String username) {
    notificationDispatcher.dispatchToUser(username);
  }

  @GetMapping("/dispatch-to-user-session")
  public @ResponseBody void dispatchToUserSession(@RequestParam String username, @RequestParam String sessionId) {
    notificationDispatcher.dispatchToUserSession(username, sessionId);
  }
}
