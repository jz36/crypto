package dev.zykov.socket.future;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zykov.entity.future.depth.FutureDepth;
import dev.zykov.entity.future.depth.FutureDepthId;
import dev.zykov.model.DepthResponse;
import dev.zykov.repository.future.FutureDepthRepository;
import dev.zykov.service.DepthCache;
import dev.zykov.service.SocketCache;
import io.micronaut.http.HttpRequest;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.reactivex.Single;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@ClientWebSocket("/ws/{symbol}@depth@100ms")
public abstract class FutureDepthStream implements AutoCloseable {

    private WebSocketSession session;
    private HttpRequest request;
    private String symbol;
    @Inject
    private DepthCache depthCache;
    @Inject
    private SocketCache socketCache;

    @OnOpen
    public void onOpen(String symbol, WebSocketSession session, HttpRequest request) {
        this.session = session;
        this.request = request;
        this.symbol = symbol;
        log.info("Open future depth for {}", symbol);
    }

    @OnMessage
    public void onMessage(DepthResponse message) {
        socketCache.addFutureDepth(message);
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
