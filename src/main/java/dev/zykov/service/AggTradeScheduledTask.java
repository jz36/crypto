package dev.zykov.service;

import dev.zykov.socket.future.FutureSocketClient;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class AggTradeScheduledTask {

    private final FutureSocketClient futureSocketClient;

    @Scheduled(fixedRate = "23h")
    public void runAggTrade() {
        futureSocketClient.closeAggTrade();
        futureSocketClient.runAggTrade();
    }

}
