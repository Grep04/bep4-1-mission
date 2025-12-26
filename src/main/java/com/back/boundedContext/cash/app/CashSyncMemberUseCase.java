package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.global.EventPublsiher.EventPublisher;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.cash.event.CashMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashSyncMemberUseCase {
    private final CashMemberRepository cashMemberRepository;
    private final EventPublisher eventPublisher;

    public CashMember syncMember(MemberDto member) {
        boolean isNew = !cashMemberRepository.existsById(member.getId());

        CashMember _member = cashMemberRepository.save(
                new CashMember(
                        member.getId(),
                        member.getCreateDate(),
                        member.getModifyDate(),
                        member.getUsername(),
                        "",
                        member.getNickname(),
                        member.getActivityScore()
                )
        );

        if (isNew) {
            // CashMemberCreatedEvent 를 발행하여 Wallet 생성 처리
            // 강력한 모듈화와 시스템 확장성을 위한 설계 (EDD)
            /*
                1. 단일 책임 원칙 (Single Responsibility Principle)
                2. 서비스 간 및 모듈 간 느슨한 결합 (Loose Coupling)
                3. 장애 격리 및 복구 (Fault Tolerance)
            */
            eventPublisher.publish(
                    new CashMemberCreatedEvent(
                            new CashMemberDto(_member)
                    )
            );
        }

        return _member;
    }
}
