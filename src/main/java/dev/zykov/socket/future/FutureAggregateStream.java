package dev.zykov.socket.future;

import dev.zykov.entity.future.agg_trade.FutureAggTrade;
import dev.zykov.entity.future.agg_trade.FutureAggTradeId;
import dev.zykov.model.AggTradeModel;
import dev.zykov.repository.future.FutureAggTradeRepository;
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

@Slf4j
@ClientWebSocket("/ws/{symbol}@aggTrade")
public abstract class FutureAggregateStream implements AutoCloseable {

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
        log.info("Open future aggTrade for {}", symbol);
    }

    @OnMessage
    public void onMessage(AggTradeModel message) {
        socketCache.addFutureAggTrade(message);
    }

    @OnClose
    public void onClose() {
        log.info("Close future aggTrade for {}", symbol);
    }

    public WebSocketSession getSession() {
        return session;
    }

}
