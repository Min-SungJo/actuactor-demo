package com.ride.actuactordemo.healthIndicator.AbstractHealthIndicator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // TODO 세부 상태 정보를 작성합니다.
        // 예외를 던지면, 예외 메시지와 함께 상태가 DOWN 으로 변경됩니다.

        // 이 코드는 항상 애플리케이션을 'UP' 상태로 보고하며,
        // 추가적인 세부 정보로 애플리케이션이 정상 작동 중임을 알립니다.

        builder.up() // 애플리케이션의 상태를 'UP'으로 설정
                .withDetail("app", "Alive and Kicking") // 커스텀 상태 정보 추가
                .withDetail("error", "Nothing! I'm good."); // 에러 정보 추가(여기서는 에러가 없음을 표시)
    }

    @Override
    public Health getHealth(boolean includeDetails) {
        return super.getHealth(includeDetails); // 상태 정보를 포함하여 Health 정보 반환
    }
}