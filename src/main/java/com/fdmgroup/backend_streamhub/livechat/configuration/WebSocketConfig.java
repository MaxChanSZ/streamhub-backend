package com.fdmgroup.backend_streamhub.livechat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
        .addEndpoint(
            "/chat",
            "/video-sync",
            "/emoji") // Web client connects to web server via '/chat' endpoint
        .setAllowedOrigins("http://localhost:5173") // allow requests from React app
        .withSockJS(); // client should connect with SockJS

    // withSockJs will allow the websockets to work even if the browser does not support
    // web sockets
    //
    // registry.addEndpoint("/video-sync").setAllowedOrigins("http://localhost:5173").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic"); // topic prefix (e.g. "/topic/hello")
    registry.setApplicationDestinationPrefixes(
        "/app"); // application prefix - where to send messages to
  }
}
