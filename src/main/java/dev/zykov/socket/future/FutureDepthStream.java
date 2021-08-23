package dev.zykov.socket.future;

import dev.zykov.model.DepthResponse;
import dev.zykov.service.DepthCache;
import io.micronaut.http.HttpRequest;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@ClientWebSocket("/ws/{symbol}@depth@100ms")
public abstract class FutureDepthStream implements AutoCloseable{

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
        depthCache.getFutureDepthCache().get(symbol).setStartUpTime(LocalDateTime.now());
        log.info("Open future depth for {}", symbol);
    }

    @OnMessage
    public void onMessage(DepthResponse message) {
        depthCache.addFutureCacheValues(message.getSymbol().toLowerCase(Locale.ROOT), message);
    }

    @OnClose
    public void onClose() {
        log.info("Close future depth for {}", symbol);
    }

    public WebSocketSession getSession() {
        return session;
    }
}
