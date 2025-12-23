package com.back.global.EventPublsiher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher {
    // 스프링의 ApplicationEventPublisher를 주입받아 이벤트를 발행하는 역할을 수행
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(Object event) {
        applicationEventPublisher.publishEvent(event);
    }
}
