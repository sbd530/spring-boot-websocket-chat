package study.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.chat.dto.ChatRoom;
import study.chat.service.ChatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public ChatRoomListWrapper<ChatRoom> findAllRoom() {
        List<ChatRoom> allRoom = chatService.findAllRoom();
        return new ChatRoomListWrapper<>(allRoom);
    }

    private static class ChatRoomListWrapper<T extends ChatRoom> {
        List<T> rooms;
        public ChatRoomListWrapper(List<T> rooms) {
            this.rooms = rooms;
        }
    }

}
