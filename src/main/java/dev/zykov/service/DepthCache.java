package dev.zykov.service;

import dev.zykov.model.DepthResponse;
import dev.zykov.model.SymbolDepthCache;
import jakarta.inject.Singleton;

import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class DepthCache {

    private final ConcurrentHashMap<String, SymbolDepthCache> depthCache;

    public DepthCache() {
        depthCache = new ConcurrentHashMap<>();
        depthCache.put("bnbbtc", new SymbolDepthCache());
    }

    public void addCacheValues(DepthResponse depthResponse) {
        var symbolDepthCache = depthCache.get("bnbbtc");
        symbolDepthCache.putBids(depthResponse.getBids());
        symbolDepthCache.putAsks(depthResponse.getAsks());
    }

    public ConcurrentHashMap<String, SymbolDepthCache> getDepthCache() {
        return depthCache;
    }

}
