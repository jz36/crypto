package dev.zykov.service;

import dev.zykov.socket.spot.SpotSocketClient;
import dev.zykov.socket.spot.SpotTradeStream;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class TradeScheduledTask {

    @Inject
    private SpotSocketClient spotSocketClient;

    @Scheduled(fixedRate = "23h")
    public void runSpotrade() {
        spotSocketClient.closeTrade();
        spotSocketClient.runTrade();
    }
}
