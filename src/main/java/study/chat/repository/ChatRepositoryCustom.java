package study.chat.repository;

import study.chat.repository.dto.ChatData;

import java.util.List;

public interface ChatRepositoryCustom {
    List<String> findUsernameByBadWords(String username);
    List<ChatData> findChatDataByUsernameAndKeyword(String username, String keyword);
}
