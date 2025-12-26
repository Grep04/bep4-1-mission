package com.back.boundedContext.post.in;

import com.back.boundedContext.post.app.PostFacade;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PostEventListner {
    private final PostFacade postFacade;

    /*
        1. @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
        이 어노테이션은 이벤트 리스너가 "이벤트를 발행한 본래 트랜잭션이 성공적으로 커밋된 직후"에 실행되도록 보장합니다.
        역할: 이벤트 발행 측(예: 회원가입 로직)의 DB 처리가 완벽히 끝난 것을 확인하고 후속 작업을 진행합니다.

        2. @Transactional(propagation = Propagation.REQUIRES_NEW)
        이 설정은 후속 작업을 **"기존 트랜잭션과는 완전히 분리된 새로운 트랜잭션"**에서 실행하겠다는 의미입니다.
        역할: AFTER_COMMIT 시점에는 이미 이전 트랜잭션이 커밋되어 종료된 상태입니다. 다른 DB 작업을 하려면 새로운 연결(Connection)과 트랜잭션이 필요합니다.
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(MemberJoinedEvent event) {
        postFacade.syncMember(event.getMember());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleMemberModifiedEvent(MemberModifiedEvent event) {
        postFacade.syncMember(event.getMember());
    }
}
