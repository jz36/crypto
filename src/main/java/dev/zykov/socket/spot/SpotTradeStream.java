package dev.zykov.socket.spot;

import dev.zykov.model.AggTradeModel;
import dev.zykov.model.TradeModel;
import dev.zykov.service.DepthCache;
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
@ClientWebSocket("/ws/{symbol}@trade")
public abstract class SpotTradeStream implements AutoCloseable{

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
        log.info("Open spot trade for {}", symbol);
    }

    @OnMessage
    public void onMessage(TradeModel message) {
        socketCache.addSpotTrade(message);
    }

    @OnClose
    public void onClose() {
        log.info("Close spot trade for {}", symbol);
    }

    public WebSocketSession getSession() {
        return session;
    }
}
