package dev.zykov.service;

import dev.zykov.socket.MainClientSocket;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class DepthScheduledTask {

    private final MainClientSocket mainClientSocket;
    private final DepthCache depthCache;

    @Scheduled(fixedRate = "${reRunSocketTimeRate: 23h}" )
    public void reRunSockets() {
        mainClientSocket.closeAllSockets();
        depthCache.reCreateCache();
        mainClientSocket.test();
    }
}
