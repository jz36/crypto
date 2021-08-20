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
    private final ConcurrentHashMap<String, SymbolDepthCache> depthCache;

    public DepthCache(@Value("${symbols}") List<String> symbols) {
        depthCache = new ConcurrentHashMap<>();
        this.symbols = symbols;
        this.symbols.forEach(symbol -> depthCache.put(symbol, new SymbolDepthCache()));
    }

    public void addCacheValues(String symbol, DepthResponse depthResponse) {
        var symbolDepthCache = depthCache.get(symbol);
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

    public ConcurrentHashMap<String, SymbolDepthCache> getDepthCache() {
        return depthCache;
    }

    public void applySnapshot(String symbol, DepthRestResponse depthRestResponse) {
        var symbolDepthCache = depthCache.get(symbol);
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
}

