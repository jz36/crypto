package dev.zykov.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@AllArgsConstructor
@Data
@Introspected
public class SymbolDepthCache {

    private Long lastUpdateId;
    private ConcurrentLinkedQueue<DepthResponse> depthResponses;
    private final ConcurrentHashMap<String, NavigableMap<BigDecimal, BigDecimal>> cache;

    public SymbolDepthCache() {
        cache = new ConcurrentHashMap<>();
        cache.put("bids", new TreeMap<>(Comparator.reverseOrder()));
        cache.put("asks", new TreeMap<>(Comparator.reverseOrder()));
        depthResponses = new ConcurrentLinkedQueue<>();
        lastUpdateId = -1L;
    }

    public void putBids(List<List<BigDecimal>> bids) {
        var bidsMap = cache.get("bids");
        bids.forEach(bid -> {
            if (bid.get(1).compareTo(BigDecimal.ZERO) == 0)
                bidsMap.remove(bid.get(0));
            else
                bidsMap.put(bid.get(0), bid.get(1));
        });
    }

    public void putAsks(List<List<BigDecimal>> asks) {
        var asksMap = cache.get("asks");
        asks.forEach(ask -> {
            if (ask.get(1).compareTo(BigDecimal.ZERO) == 0)
                asksMap.remove(ask.get(0));
            else
                asksMap.put(ask.get(0), ask.get(1));
        });
    }

    public boolean isCached() {
        return !lastUpdateId.equals(-1L);
    }
}
