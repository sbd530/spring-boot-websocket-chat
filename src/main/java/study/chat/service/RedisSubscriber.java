package study.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import study.chat.dto.ChatMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    // 레디스에서 메세지가 발행되면 onMessage 가 해당 메세지 처리
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            //레디스에서 발행된 데이터 역직렬화
            String publishMessage = (String) redisTemplate
                    .getStringSerializer().deserialize(message.getBody());
            //사용 객체로 맵핑
            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            //구독자에게 메세지 전송
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
