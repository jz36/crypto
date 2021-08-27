package dev.zykov.socket.spot;

import dev.zykov.rest.SpotRestClient;
import dev.zykov.service.DepthCache;
import dev.zykov.socket.future.FutureKlineCandlestickStream;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava2.http.client.websockets.RxWebSocketClient;
import io.micronaut.websocket.CloseReason;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SpotSocketClient {

    @Inject
    @Client("https://stream.binance.com:9443")
    private RxWebSocketClient spotWebSocketClient;

    @Inject
    private DepthCache depthCache;
    @Inject
    private SpotRestClient spotRestClient;

    @Value("${symbols}")
    private List<String> symbols;

    private ConcurrentHashMap<String, SpotDepthStream> spotStreamsMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, SpotAggregateStream> spotAggTradeMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, SpotTradeStream> spotTradeMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, SpotKlineCandlestickStream> spotKlineCandlestickMap = new ConcurrentHashMap<>();



    public void runSpotSockets() {
        depthCache.getSpotDepthCache().keySet()
                .forEach(symbol -> {
                            if (!spotStreamsMap.containsKey(symbol)) {
                                spotStreamsMap.put(symbol, spotWebSocketClient.connect(SpotDepthStream.class, String.format("/ws/%s@depth@100ms", symbol)).blockingFirst());
                            }
                            if (!depthCache.getSpotDepthCache().get(symbol).isCached()) {
                                spotRestClient.getDepth(symbol.toUpperCase(Locale.ROOT), 1000)
                                        .thenAcceptAsync(response -> depthCache.applySpotSnapshot(symbol, response));
                            }
                        }
                );
    }

    public void closeAllSpotSockets() {
        spotStreamsMap.forEach((k, v) -> {
            if (v != null && v.getSession() != null)
                v.getSession().close(CloseReason.NORMAL);
        });
        spotStreamsMap.clear();
    }

    public void runAggTrade() {
        symbols.forEach(t -> spotAggTradeMap.put(
                t,
                spotWebSocketClient.connect(SpotAggregateStream.class,
                        String.format("/ws/%s@aggTrade", t)).blockingFirst()
        ));
    }

    public void closeAggTrade() {
        spotAggTradeMap.forEach((k, v) -> {
            if (v != null && v.getSession() != null)
                v.getSession().close(CloseReason.NORMAL);
        });
        spotAggTradeMap.clear();
    }

    public void runTrade() {
        symbols.forEach(t -> spotTradeMap.put(
                t,
                spotWebSocketClient.connect(SpotTradeStream.class,
                        String.format("/ws/%s@trade", t)).blockingFirst()
        ));
    }

    public void closeTrade() {
        spotTradeMap.forEach((k, v) -> {
            if (v != null && v.getSession() != null)
                v.getSession().close(CloseReason.NORMAL);
        });
        spotTradeMap.clear();
    }

    public void runKlineCandlestick() {
        symbols.forEach(t -> spotKlineCandlestickMap.put(
                t,
                spotWebSocketClient.connect(SpotKlineCandlestickStream.class,
                        String.format("/ws/%s@kline_1m", t)).blockingFirst()
        ));
    }

    public void closeKlineCandlestick() {
        spotKlineCandlestickMap.forEach((k,v) -> {
            if (v != null && v.getSession() != null)
                v.getSession().close(CloseReason.NORMAL);
        });
        spotKlineCandlestickMap.clear();
    }
}
