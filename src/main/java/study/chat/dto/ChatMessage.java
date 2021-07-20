package study.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    public enum MessageType {
        ENTER, JOIN, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
