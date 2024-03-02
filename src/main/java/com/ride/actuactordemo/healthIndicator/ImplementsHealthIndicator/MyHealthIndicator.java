package com.ride.actuactordemo.healthIndicator.ImplementsHealthIndicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class MyHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // 이 코드는 대부분의 경우 애플리케이션을 'UP' 상태로 보고하지만,
        // 10% 확률로 'DOWN' 상태를 보고하여 임의의 실패를 시뮬레이션합니다.
        double chance = ThreadLocalRandom.current().nextDouble(); // 0.0과 1.0 사이의 랜덤 값 생성
        Health.Builder status = Health.up(); // 기본적으로 상태를 'UP'으로 설정
        if (chance > 0.9) { // 10% 확률로 상태를 'DOWN'으로 변경
            status = Health.down();
        }
        return status // 최종 헬스 상태 반환
                .withDetail("chance", chance)
                .withDetail("strategy", "thread-local")
                .build();
    }
}