package dev.zykov.service;

import dev.zykov.socket.future.FutureSocketClient;
import dev.zykov.socket.spot.SpotSocketClient;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class DepthScheduledTask {

    private final SpotSocketClient spotSocketClient;
    private final FutureSocketClient futureSocketClient;
    private final DepthCache depthCache;

//    @Scheduled(fixedRate = "${reRunSocketTimeRate: 23h}" )
    public void reRunSpotSockets() {
        spotSocketClient.closeAllSpotSockets();
        depthCache.reCreateSpotCache();
        spotSocketClient.runSpotSockets();
    }

//    @Scheduled(fixedRate = "${reRunSocketTimeRate: 23h}" )
    public void reRunFutureSockets() {
        futureSocketClient.closeAllFutureSockets();
        depthCache.reCreateFutureCache();
        futureSocketClient.runFutureSockets();
    }
}
