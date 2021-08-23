package dev.zykov.socket.spot;

import dev.zykov.rest.SpotRestClient;
import dev.zykov.service.DepthCache;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava2.http.client.websockets.RxWebSocketClient;
import io.micronaut.websocket.CloseReason;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

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

    private ConcurrentHashMap<String, SpotDepthStream> spotStreamsMap = new ConcurrentHashMap<>();

    public void runSpotSockets() {
        depthCache.getSpotDepthCache().keySet()
                .forEach(symbol -> {
                            if (!spotStreamsMap.containsKey(symbol)) {
                                spotStreamsMap.put(symbol, spotWebSocketClient.connect(SpotDepthStream.class, String.format("/ws/%s@depth@100ms", symbol)).blockingFirst());
                            }
                            if (!depthCache.getSpotDepthCache().get(symbol).isCached()) {
                                spotRestClient.getDepth(symbol.toUpperCase(Locale.ROOT), 1000)
                                        .thenAcceptAsync(response -> depthCache.applySnapshot(symbol, response));
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
}