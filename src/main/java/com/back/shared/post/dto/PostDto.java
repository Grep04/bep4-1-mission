package com.back.shared.post.dto;

import com.back.boundedContext.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// Lombok이 생성자를 만들 때, 그 생성자 위에 특정한 어노테이션을 붙여달라고 지시
// @JsonCreator: Jackson이 JSON 데이터를 자바 객체로 역직렬화할 때
// 기본 생성자가 아닌 이 생성자를 사용해서 객체를 만들라고 지정
// ※ Dto 객체의 필드에 final이 붙어 불변 객체로 만들 때 유용
@AllArgsConstructor(
    onConstructor_ = @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
)
@Getter
public class PostDto {
    private final int id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final int authorId;
    private final String authorName;
    private final String title;
    private final String content;

    public PostDto(Post post) {
        this(
                post.getId(),
                post.getCreateDate(),
                post.getModifyDate(),
                post.getAuthor().getId(),
                post.getAuthor().getNickname(),
                post.getTitle(),
                post.getContent()
        );
    }
}