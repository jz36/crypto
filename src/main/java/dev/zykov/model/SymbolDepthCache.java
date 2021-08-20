package dev.zykov.model;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Data
@Introspected
public class SymbolDepthCache {

    private final ConcurrentHashMap<String, NavigableMap<BigDecimal, BigDecimal>> cache;

    public SymbolDepthCache() {
        cache = new ConcurrentHashMap<>();
        cache.put("bids", new TreeMap<>(Comparator.reverseOrder()));
        cache.put("asks", new TreeMap<>(Comparator.reverseOrder()));
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
}
