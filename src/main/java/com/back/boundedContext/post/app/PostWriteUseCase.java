package com.back.boundedContext.post.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.EventPublsiher.EventPublisher;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriteUseCase {
    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;

    public Post write(Member author, String title, String content) {
        Post post = postRepository.save(new Post(author, title, content));

        // 결합도 분리를 위한 이벤트 발행
        eventPublisher.publish(
                new PostCreatedEvent(new PostDto(post))
        );

        return post;
    }
}
