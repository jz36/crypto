package dev.zykov.service;

import dev.zykov.model.DepthResponse;
import dev.zykov.model.DepthRestResponse;
import dev.zykov.model.SymbolDepthCache;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class DepthCache {

    private List<String> symbols;
    private final ConcurrentHashMap<String, SymbolDepthCache> spotDepthCache;
    private final ConcurrentHashMap<String, SymbolDepthCache> futureDepthCache;

    public DepthCache(@Value("${symbols}") List<String> symbols) {
        spotDepthCache = new ConcurrentHashMap<>();
        futureDepthCache = new ConcurrentHashMap<>();
        this.symbols = symbols;
        this.symbols.forEach(symbol -> {
            spotDepthCache.put(symbol, new SymbolDepthCache());
            futureDepthCache.put(symbol, new SymbolDepthCache());
        });
    }

    public void addSpotCacheValues(String symbol, DepthResponse depthResponse) {
        var symbolDepthCache = spotDepthCache.get(symbol);
        addCacheValues(symbolDepthCache, depthResponse);
    }

    public void addFutureCacheValues(String symbol, DepthResponse depthResponse) {
        var symbolDepthCache = futureDepthCache.get(symbol);
        addCacheValues(symbolDepthCache, depthResponse);
    }

    private void addCacheValues(SymbolDepthCache symbolDepthCache, DepthResponse depthResponse) {
        if (symbolDepthCache.isCached()) {
            if (depthResponse.getLastUpdateId() > symbolDepthCache.getLastUpdateId()) {
                symbolDepthCache.setLastUpdateId(depthResponse.getLastUpdateId());
                symbolDepthCache.putBids(depthResponse.getBids());
                symbolDepthCache.putAsks(depthResponse.getAsks());
            }
        } else {
            symbolDepthCache.getDepthResponses().add(depthResponse);
        }
    }

    public ConcurrentHashMap<String, SymbolDepthCache> getSpotDepthCache() {
        return spotDepthCache;
    }
    public ConcurrentHashMap<String, SymbolDepthCache> getFutureDepthCache() {
        return futureDepthCache;
    }

    public void applySpotSnapshot(String symbol, DepthRestResponse depthRestResponse) {
        var symbolDepthCache = spotDepthCache.get(symbol);
        applySnapshot(depthRestResponse, symbolDepthCache);
    }

    public void applyFutureSnapshot(String symbol, DepthRestResponse depthRestResponse) {
        var symbolDepthCache = futureDepthCache.get(symbol);
        applySnapshot(depthRestResponse, symbolDepthCache);
    }

    private void applySnapshot(DepthRestResponse depthRestResponse, SymbolDepthCache symbolDepthCache) {
        symbolDepthCache.setLastUpdateId(depthRestResponse.getLastUpdateId());
        symbolDepthCache.putBids(depthRestResponse.getBids());
        symbolDepthCache.putAsks(depthRestResponse.getAsks());
        symbolDepthCache.getDepthResponses().stream()
                .filter(t -> t.getLastUpdateId() > depthRestResponse.getLastUpdateId())
                .forEach(t -> {
                    symbolDepthCache.putBids(t.getBids());
                    symbolDepthCache.putAsks(t.getAsks());
                });
        symbolDepthCache.setDepthResponses(null);
    }

    public void reCreateSpotCache() {
        spotDepthCache.clear();
        symbols.forEach(symbol -> spotDepthCache.put(symbol, new SymbolDepthCache()));
    }

    public void reCreateFutureCache() {
        futureDepthCache.clear();
        symbols.forEach(symbol -> futureDepthCache.put(symbol, new SymbolDepthCache()));
    }
}

