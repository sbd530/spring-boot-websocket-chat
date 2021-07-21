package study.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.chat.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long>, ChatRepositoryCustom {


}
