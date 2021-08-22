package dev.zykov.socket;

import dev.zykov.model.DepthResponse;
import dev.zykov.service.DepthCache;
import io.micronaut.http.HttpRequest;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import jakarta.inject.Inject;

import java.util.Locale;

@ClientWebSocket("/ws/{symbol}@depth@100ms")
public abstract class DepthStream implements AutoCloseable{

    private WebSocketSession session;
    private HttpRequest request;
    private String symbol;
    @Inject
    private DepthCache depthCache;

    @OnOpen
    public void onOpen(String symbol, WebSocketSession session, HttpRequest request) {
        this.session = session;
        this.request = request;
        this.symbol = symbol;
        System.out.printf("Open for %s\n", symbol);
    }

    @OnMessage
    public void onMessage(DepthResponse message) {
        depthCache.addCacheValues(message.getSymbol().toLowerCase(Locale.ROOT), message);
    }

    @OnClose
    public void onClose() {
        System.out.printf("Close for %s", symbol);
    }

}
