package dev.zykov.service;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class SocketCacheScheduledTask {

    private final SocketCache socketCache;

    @Scheduled(fixedRate = "1m", initialDelay = "1m")
    public void saveFutureDepth(){
        socketCache.saveAndResetFutureDepth();
    }

    @Scheduled(fixedRate = "1m", initialDelay = "1m")
    public void saveSpotDepth(){
        socketCache.saveAndResetSpotDepth();
    }

    @Scheduled(fixedRate = "1m", initialDelay = "1m")
    public void saveFutureAggTrade(){
        socketCache.saveAndResetFutureAggTrade();
    }

    @Scheduled(fixedRate = "1m", initialDelay = "1m")
    public void saveSpotAggTrade(){
        socketCache.saveAndResetSpotAggTrade();
    }

    @Scheduled(fixedRate = "1m", initialDelay = "1m")
    public void saveSpotTrade(){
        socketCache.saveAndResetSpotTrade();
    }
}
