package com.back.boundedContext.member.eventListner;

import com.back.boundedContext.member.entity.Member;
import com.back.boundedContext.member.service.MemberService;
import com.back.shared.post.event.PostCommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberEventListner {
    private final MemberService memberService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(PostCreatedEvent event){
        Member member = memberService.findById(event.getPost().getAuthorId()).get();

        // member 도메인에서 관리해야 할 score임으로 직접 스코어 정보 결정
        // 즉, 이벤트 파라미터로 스코어 정보를 넘기는 것이 비효율적인 방법 (ex. 유지 보수 측면)
        member.increaseActivityScore(3);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleCommentCreatedEvent(PostCommentCreatedEvent event) {
        Member member = memberService.findById(event.getPostComment().getAuthorId()).get();
        member.increaseActivityScore(1);
    }
}
