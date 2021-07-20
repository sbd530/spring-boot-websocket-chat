package study.chat.repository;

import org.springframework.data.redis.listener.ChannelTopic;
import study.chat.dto.ChatRoom;

import javax.annotation.PostConstruct;
import java.util.*;

//@Repository
public class ChatRoomRepositoryMap implements ChatRoomRepository {

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    @Override
    public List<ChatRoom> findAllRoom() {
        List<ChatRoom> chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    @Override
    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    @Override
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    @Override
    public void enterChatRoom(String roomId) {

    }

    @Override
    public ChannelTopic getTopic(String roomId) {
        return null;
    }

}
