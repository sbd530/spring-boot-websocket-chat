package study.chat.repository;

import org.springframework.data.redis.listener.ChannelTopic;
import study.chat.dto.ChatRoom;

import java.util.List;

public interface ChatRoomRepository {

    List<ChatRoom> findAllRoom();

    ChatRoom findRoomById(String id);

    ChatRoom createRoom(String name);

    void enterChatRoom(String roomId);

    ChannelTopic getTopic(String roomId);
}
