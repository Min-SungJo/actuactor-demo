package com.ride.actuactordemo.healthIndicator.reactiveHealthIndicator;

import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Mono;

@Component("httpbin")
@ConditionalOnEnabledHealthIndicator("httpbin")
public class RestClientServiceHealthIndicator implements ReactiveHealthIndicator {

    private final RestClient restClient;

    public RestClientServiceHealthIndicator() {
        this.restClient = RestClient.create("https://httpbin.org");
    }

    @Override
    public Mono<Health> health() {
        return Mono.fromSupplier(() -> restClient.get()
                        .uri("/status/200") // 상태 체크를 위한 URI
                        .retrieve() // HTTP GET 요청 실행
                        .toBodilessEntity()) // 응답 본문 없이 엔티티만 반환
                .map(responseEntity -> {
                    // 응답 상태 코드가 200 OK인 경우, 서비스 상태를 'UP'으로 설정
                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        return Health.up().withDetail("status", "서비스가 정상입니다.").build();
                    } else {
                        // 그 외의 경우, 서비스 상태를 'DOWN'으로 설정
                        return Health.down().withDetail("status", "서비스에 문제가 있습니다.")
                                .withDetail("statusCode", responseEntity.getStatusCodeValue()).build();
                    }
                })
                .onErrorResume(e -> Mono.just(Health.down((Exception) e).build())); // 오류 처리
    }
}
