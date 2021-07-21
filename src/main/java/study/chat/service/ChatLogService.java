package study.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.chat.repository.ChatRepository;

@Service
@RequiredArgsConstructor
public class ChatLogService {

    private final ChatRepository chatRepository;


}
