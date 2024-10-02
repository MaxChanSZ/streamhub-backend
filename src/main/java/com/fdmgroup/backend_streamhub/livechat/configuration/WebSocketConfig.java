package com.fdmgroup.backend_streamhub.livechat.configuration;

import com.fdmgroup.backend_streamhub.livechat.service.WebSocketHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Autowired
  WebSocketHandshakeInterceptor handshakeInterceptor;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
        .addEndpoint(
                "/chat",
                "/video-sync",
                "/emoji")
        .addInterceptors(handshakeInterceptor) // interceptor authenticates websocket connection
        .setAllowedOrigins("http://localhost:5173") // allow requests from React app
        .withSockJS(); // client should connect with SockJS
    // withSockJs will allow the websockets to work even if the browser does not support
    // web sockets

  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic"); // topic prefix (e.g. "/topic/hello")
    registry.setApplicationDestinationPrefixes(
        "/app"); // application prefix - where to send messages to
  }
}
