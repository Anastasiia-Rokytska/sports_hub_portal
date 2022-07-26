package com.company.sportHubPortal.Configs;

import com.company.sportHubPortal.Security.CustomUserDetails;
import com.sun.security.auth.UserPrincipal;
import java.security.Principal;
import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

  @Override
  protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                    Map<String, Object> attributes) {
    UserDetails userDetails =
        (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return new UserPrincipal(userDetails.getUsername());
  }
}
