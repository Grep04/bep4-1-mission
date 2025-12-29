package com.back.global.global;

import com.back.global.EventPublsiher.EventPublisher;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {
    /**
     * [장점]
     * 접근성: 코드 어디에서든(Spring Bean이 아닌 곳에서도) GlobalConfig.getEventPublisher().publish(...)와 같은 식으로 편리하게 사용할 수 있습니다.
     * 유연성: 도메인 모델 내부에 비즈니스 로직을 응집시킬 때 외부 인프라 서비스(이벤트 발행 등)를 호출하기 용이합니다.
     * [위험성 (주의할 점)]
     * 테스트의 어려움: static 필드는 전역 상태(Global State)를 만듭니다. 단위 테스트를 수행할 때 여러 테스트 케이스 사이에서 상태가 공유되거나, Mock 객체로 교체하기가 까다로울 수 있습니다.     * 순환 참조 및 시점 문제: Spring Context가 완전히 초기화되기 전에 static 필드에 접근하면 null이 반환될 위험이 있습니다.
     * 객체지향 설계 위반: 의존성이 명시적이지 않고 숨겨져 있어 코드의 흐름을 파악하기 어렵게 만들 수 있습니다.
     */

    @Getter
    private static EventPublisher eventPublisher;

    @Autowired
    public void setEventPublisher(EventPublisher eventPublisher) {
        GlobalConfig.eventPublisher = eventPublisher;
    }

    public static String INTERNAL_CALL_BACK_URL;
}
