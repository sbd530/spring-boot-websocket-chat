package study.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.chat.entity.BadWord;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitBadWord {

    private final InitBadWordService initBadWordService;

    @PostConstruct
    public void init() {
        initBadWordService.init();
    }

    @Component
    static class InitBadWordService {

        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            List<BadWord> words = new ArrayList<>();
            words.add(new BadWord("욕설1", BadWord.Type.WARNED));
            words.add(new BadWord("욕설2", BadWord.Type.WARNED));
            words.add(new BadWord("금지1", BadWord.Type.FORBIDDEN));
            words.add(new BadWord("금지2", BadWord.Type.FORBIDDEN));
            for (BadWord word : words) {
                em.persist(word);
            }
        }
    }
}

