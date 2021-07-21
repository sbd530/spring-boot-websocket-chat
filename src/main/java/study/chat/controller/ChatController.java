package study.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import study.chat.common.jwt.JwtTokenProvider;
import study.chat.dto.ChatMessage;
import study.chat.repository.ChatRoomRepository;
import study.chat.service.ChatService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    // /pub/chat/message 로 오는 메세징을 처리한다.
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("token") String token) { //JWT 유효성 검증 추가
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);

        message.setSender(nickname);
        //인원수 설정
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
        // Redis 로 발행
        chatService.sendChatMessage(message);
    }
}
