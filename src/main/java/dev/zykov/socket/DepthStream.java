package dev.zykov.socket;

import dev.zykov.model.DepthResponse;
import dev.zykov.service.DepthCache;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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
        System.out.printf("Open for %s", symbol);
    }

    @OnMessage
    public void onMessage(DepthResponse message) {
        depthCache.addCacheValues(message);
    }

    @OnClose
    public void onClose() {
        System.out.printf("Close for %s", symbol);
    }

}
