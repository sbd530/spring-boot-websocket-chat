package study.chat.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebSockConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Slf4j
    @Component
    public class WebSockChatHandler extends TextWebSocketHandler {
        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            String payload = message.getPayload();
            log.info("payload {}", payload);
            TextMessage textMessage = new TextMessage("Welcome To MyChat!");
            session.sendMessage(textMessage);
        }
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketHandler, "/ws/chat")
                .setAllowedOrigins("*");
    }
}
