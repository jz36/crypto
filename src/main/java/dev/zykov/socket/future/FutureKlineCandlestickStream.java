package dev.zykov.socket.future;

import dev.zykov.model.AggTradeModel;
import dev.zykov.model.KlineCandlestickModel;
import dev.zykov.service.SocketCache;
import io.micronaut.http.HttpRequest;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ClientWebSocket("/ws/{symbol}@kline_1m")
public abstract class FutureKlineCandlestickStream implements AutoCloseable {

    private WebSocketSession session;
    private HttpRequest request;
    private String symbol;
    @Inject
    private SocketCache socketCache;

    @OnOpen
    public void onOpen(String symbol, WebSocketSession session, HttpRequest request) {
        this.session = session;
        this.request = request;
        this.symbol = symbol;
        log.info("Open future klineCandlestick for {}", symbol);
    }

    @OnMessage
    public void onMessage(KlineCandlestickModel message) {
        socketCache.addFutureKlineCandlestick(message);
    }

    @OnClose
    public void onClose() {
        log.info("Close future klineCandlestick for {}", symbol);
    }

    public WebSocketSession getSession() {
        return session;
    }

}
