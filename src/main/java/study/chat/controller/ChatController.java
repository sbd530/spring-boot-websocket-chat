package study.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import study.chat.dto.ChatMessage;
import study.chat.repository.ChatRoomRepository;
import study.chat.service.RedisPublisher;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    // MessageBrokerRegistry.setApplicationDestinationPrefixes("/pub");
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {

        if (message.getType().equals(ChatMessage.MessageType.ENTER)){
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }

        // Websocket 에 발행된 메세지를 레디스로 발행해야 한다.
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);


    }
}
