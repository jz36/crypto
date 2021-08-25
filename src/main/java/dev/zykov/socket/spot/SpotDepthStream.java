package dev.zykov.socket.spot;

import dev.zykov.entity.future.depth.FutureDepth;
import dev.zykov.entity.future.depth.FutureDepthId;
import dev.zykov.entity.spot.depth.SpotDepth;
import dev.zykov.entity.spot.depth.SpotDepthId;
import dev.zykov.model.DepthResponse;
import dev.zykov.repository.spot.SpotDepthRepository;
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

import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@ClientWebSocket("/ws/{symbol}@depth@100ms")
public abstract class SpotDepthStream implements AutoCloseable{

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
        log.info("Open spot depth for {}", symbol);
    }

    @OnMessage
    public void onMessage(DepthResponse message) {
        socketCache.addSpotDepth(message);
        depthCache.addSpotCacheValues(message.getSymbol().toLowerCase(Locale.ROOT), message);
    }

    @OnClose
    public void onClose() {
        log.info("Close spot depth for {}", symbol);
    }

    public WebSocketSession getSession() {
        return session;
    }
}
