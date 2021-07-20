package study.chat.repository;

import study.chat.dto.ChatRoom;

import java.util.List;

public interface ChatRoomRepository {

    List<ChatRoom> findAllRoom();

    ChatRoom findRoomById(String id);

    ChatRoom createRoom(String name);

}
