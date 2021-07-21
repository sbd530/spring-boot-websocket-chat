package study.chat.common.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import study.chat.common.jwt.JwtTokenProvider;
import study.chat.dto.ChatMessage;
import study.chat.repository.ChatRoomRepository;
import study.chat.service.ChatService;

import java.security.Principal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    //요청 처리 전에 먼저 JWT 검증 실행한다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == StompCommand.CONNECT) { // 웹소켓 연결 요청
            String jwtToken = accessor.getFirstNativeHeader("token");
            log.info("CONNECT {}", jwtToken);
            //header 에서 jwt 검증
            jwtTokenProvider.validateToken(jwtToken);
        } else if (accessor.getCommand() == StompCommand.SUBSCRIBE) { //채팅방 구독요청

            String roomId = chatService.getRoomId(
                    Optional.ofNullable((String)message.getHeaders().get("simpDestination"))
                    .orElse("InvalidRoomId"));
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            chatRoomRepository.setUserEnterInfo(sessionId, roomId);
            chatRoomRepository.plusUserCount(roomId);

            String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser"))
                    .map(Principal::getName)
                    .orElse("UnknownUser");
            chatService.sendChatMessage(
                    ChatMessage.builder()
                    .type(ChatMessage.MessageType.ENTER)
                    .roomId(roomId)
                    .sender(name)
                    .build()
            );
            log.info("SUBSCRIBED {}, {}", name, roomId);
        } else if (accessor.getCommand() == StompCommand.DISCONNECT) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            String roomId = chatRoomRepository.getUserEnterRoomId(sessionId);
            chatRoomRepository.minusUserCount(roomId);

            String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser"))
                    .map(Principal::getName)
                    .orElse("UnknownUser");
            chatService.sendChatMessage(
                    ChatMessage.builder()
                            .type(ChatMessage.MessageType.QUIT)
                            .roomId(roomId)
                            .sender(name)
                            .build()
            );
            log.info("DISCONNECTED {}, {}", name, roomId);
        }

        return message;
    }
}
