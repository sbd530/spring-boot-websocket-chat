package study.chat.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import study.chat.repository.dto.ChatData;
import study.chat.repository.dto.QChatData;

import java.util.List;

import static study.chat.entity.QBadWord.badWord;
import static study.chat.entity.QChat.chat;


@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findUsernameByBadWords(String username) {
        List<String> badWords = queryFactory
                .select(badWord.word)
                .from(badWord)
                .fetch();

        return queryFactory
               .select(chat.username)
               .from(chat)
               .where(chat.message.in(badWords))
               .fetch();
    }

    @Override
    public List<ChatData> findChatDataByUsernameAndKeyword(String username, String keyword) {
        return queryFactory
                .select(new QChatData(
                        chat.username,
                        chat.message,
                        chat.createdDate))
                .from(chat)
                .where(chat.username.eq(username),
                        messageLike(keyword))
                .fetch();
    }

    private BooleanExpression messageLike(String keyword) {
        return StringUtils.hasText(keyword) ? chat.message.like(keyword) : null;
    }
}
