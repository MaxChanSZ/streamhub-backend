package com.fdmgroup.backend_streamhub.livechat.service;

import com.fdmgroup.backend_streamhub.authenticate.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    TokenService tokenService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        HttpHeaders headers = request.getHeaders();
//        System.out.println("Headers are: " + headers);
//        System.out.println("Interceptor be intercepting");
//        List<String> tokens = headers.get("Authorization");
//        System.out.println("Tokens are: " +  tokens);

        // can't use the above because the headers are not passed as part of the handshake
        // therefore send the token as a parameter in the URI to connect to the websocket
        UriComponents uriComponents = UriComponentsBuilder.fromUri(request.getURI()).build();

        String token = uriComponents.getQueryParams().getFirst("token");
        String partyCode = uriComponents.getQueryParams().getFirst("roomID");

        if ( token != null ) {
            if ( tokenService.isValidToken(token, partyCode) ) {
                // System.out.println("Token is valid");
                attributes.put("partyCode", partyCode);
                System.out.println("Setting user role to: " + tokenService.extractRole(token));
                attributes.put("role", tokenService.extractRole(token));
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
