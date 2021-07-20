package study.chat.service;

import org.springframework.web.socket.WebSocketSession;
import study.chat.dto.ChatRoom;

import java.util.List;

public interface ChatService {

    List<ChatRoom> findAllRoom();
    ChatRoom findRoomById(String roomId);
    ChatRoom createRoom(String name);
    <T> void sendMessage(WebSocketSession session, T message);
}
