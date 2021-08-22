package dev.zykov.socket;

import dev.zykov.model.DepthResponse;
import dev.zykov.rest.RestClient;
import dev.zykov.service.DepthCache;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava2.http.client.websockets.RxWebSocketClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


@Singleton
public class MainClientSocket {

    @Inject
    @Client("https://stream.binance.com:9443")
    private RxWebSocketClient webSocketClient;
    @Inject
    private DepthCache depthCache;
    @Inject
    private RestClient restClient;

    private ConcurrentHashMap<String, DepthStream> streamsMap = new ConcurrentHashMap<>();

    public void test() {
        depthCache.getDepthCache().keySet()
                .forEach(symbol -> {
                            if (!streamsMap.containsKey(symbol)) {
                                streamsMap.put(symbol, webSocketClient.connect(DepthStream.class, String.format("/ws/%s@depth@100ms", symbol)).blockingFirst());
                            }
                            if (!depthCache.getDepthCache().get(symbol).isCached()) {
                                restClient.getDepth(symbol.toUpperCase(Locale.ROOT), 1000)
                                        .thenAcceptAsync(response -> depthCache.applySnapshot(symbol, response));
                            }
                        }
                );
    }

}
