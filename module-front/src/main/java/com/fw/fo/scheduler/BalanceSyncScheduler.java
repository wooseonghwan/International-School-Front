package com.fw.fo.scheduler;

import com.fw.core.dto.fo.FoPaymentDTO;
import com.fw.fo.main.service.FoPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.rmi.server.ExportException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BalanceSyncScheduler {

    private final FoPaymentService foPaymentService;

    // 중복실행 방지
    private volatile boolean isRunning = false;

    @Scheduled(fixedDelay = 30000)
    public void syncBalance() {
        // 중복실행 확인
        if (isRunning) return;

        // 실행 중 플래그 변경
        isRunning = true;

        try {
            // 잔액 동기화
            foPaymentService.syncBalance();
        } catch (Exception e) {
            log.error("잔액 동기화 스케쥴 처리 중 오류발생", e);
        } finally {
            // 종료 시, 실행종료 플래그 변경
            isRunning = false;
        }
    }
}
