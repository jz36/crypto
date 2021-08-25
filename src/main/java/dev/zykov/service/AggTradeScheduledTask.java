package dev.zykov.service;

import dev.zykov.socket.future.FutureSocketClient;
import dev.zykov.socket.spot.SpotSocketClient;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class AggTradeScheduledTask {

    private final FutureSocketClient futureSocketClient;
    private final SpotSocketClient spotSocketClient;

//    @Scheduled(fixedRate = "23h")
    public void runFutureAggTrade() {
        futureSocketClient.closeAggTrade();
        futureSocketClient.runAggTrade();
    }

//    @Scheduled(fixedRate = "23h")
    public void runSpotAggTrade() {
        spotSocketClient.closeAggTrade();
        spotSocketClient.runAggTrade();
    }

}
