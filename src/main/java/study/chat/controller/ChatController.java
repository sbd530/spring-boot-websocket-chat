package study.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import study.chat.dto.ChatMessage;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    // MessageBrokerRegistry.setApplicationDestinationPrefixes("/pub");
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (message.getType().equals(ChatMessage.MessageType.ENTER))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");

        // /sub/chat/room/{roomId}
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
