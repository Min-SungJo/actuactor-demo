package com.ride.actuactordemo.healthIndicator.reactiveHealthIndicator;

import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@ConditionalOnEnabledHealthIndicator("management.health.downstreamService.enabled=false")
public class DownstreamServiceHealthIndicator implements ReactiveHealthIndicator {

    // 이 특정 서비스나 애플리케이션에서 호출되는 외부 서비스의 헬스 체크를 비동기적으로 수행하고,
    // 모든 것이 정상이면 'UP' 상태를,
    // 예외가 발생하면 해당 예외와 함께 'DOWN' 상태를 반환합니다.
    // 실제 다운스트림 서비스의 상태 확인을 위해 WebClient, RestClient 와 같은 비동기 클라이언트를 사용할 수 있습니다.

    @Override
    public Mono<Health> health() {
        return checkDownstreamServiceHealth().onErrorResume(
                ex -> Mono.just(new Health.Builder().down(ex).build()) // 에러 발생 시 'DOWN' 상태로 설정
        );
    }

    private Mono<Health> checkDownstreamServiceHealth() {
        // we could use WebClient to check health reactively
        return Mono.just(new Health.Builder().up().build()); // 기본적으로 'UP' 상태 반환
    }
}
