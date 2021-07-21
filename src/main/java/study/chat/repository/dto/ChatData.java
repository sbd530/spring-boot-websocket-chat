package study.chat.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatData {

    private String username;
    private String message;
    private LocalDateTime createdDate;

    @QueryProjection
    public ChatData(String username, String message, LocalDateTime createdDate) {
        this.username = username;
        this.message = message;
        this.createdDate = createdDate;
    }
}
