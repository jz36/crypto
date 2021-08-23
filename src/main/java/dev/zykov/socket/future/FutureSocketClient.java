package dev.zykov.socket.future;

import dev.zykov.rest.FutureRestClient;
import dev.zykov.service.DepthCache;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava2.http.client.websockets.RxWebSocketClient;
import io.micronaut.websocket.CloseReason;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class FutureSocketClient {

    @Inject
    @Client("https://fstream.binance.com")
    private RxWebSocketClient futureWebSocketClient;
    @Inject
    private DepthCache depthCache;
    @Inject
    private FutureRestClient futureRestClient;

    private ConcurrentHashMap<String, FutureDepthStream> futureStreamsMap = new ConcurrentHashMap<>();

    public void runFutureSockets() {
        depthCache.getFutureDepthCache().keySet()
                .forEach(symbol -> {
                            if (!futureStreamsMap.containsKey(symbol)) {
                                futureStreamsMap.put(symbol, futureWebSocketClient.connect(FutureDepthStream.class, String.format("/ws/%s@depth@100ms", symbol)).blockingFirst());
                            }
                            if (!depthCache.getFutureDepthCache().get(symbol).isCached()) {
                                futureRestClient.getDepth(symbol.toUpperCase(Locale.ROOT), 1000)
                                        .thenAcceptAsync(response -> depthCache.applyFutureSnapshot(symbol, response));
                            }
                        }
                );
    }

    public void closeAllFutureSockets() {
        futureStreamsMap.forEach((k, v) -> {
            if (v != null && v.getSession() != null)
                v.getSession().close(CloseReason.NORMAL);
        });
        futureStreamsMap.clear();
    }
}
