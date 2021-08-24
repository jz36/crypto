package dev.zykov.socket.future;

import dev.zykov.model.DepthResponse;
import dev.zykov.model.future.AggTrade;
import dev.zykov.repository.AggTradeRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.reactivex.Single;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
@ClientWebSocket("/ws/{symbol}@aggTrade")
public abstract class FutureAggregateStream implements AutoCloseable {

    private WebSocketSession session;
    private HttpRequest request;
    private String symbol;
    @Inject
    private AggTradeRepository aggTradeRepository;

    @OnOpen
    public void onOpen(String symbol, WebSocketSession session, HttpRequest request) {
        this.session = session;
        this.request = request;
        this.symbol = symbol;
        log.info("Open future aggTrade for {}", symbol);
    }

    @OnMessage
    public void onMessage(AggTrade message) {
        Single.fromPublisher(aggTradeRepository.save(message)).subscribe();
    }

    @OnClose
    public void onClose() {
        log.info("Close future aggTrade for {}", symbol);
    }

    public WebSocketSession getSession() {
        return session;
    }

}
