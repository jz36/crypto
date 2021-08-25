package dev.zykov.socket.spot;

import dev.zykov.entity.spot.agg_trade.SportAggTradeId;
import dev.zykov.entity.spot.agg_trade.SpotAggTrade;
import dev.zykov.model.AggTradeModel;
import dev.zykov.repository.spot.SpotAggTradeRepository;
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
public abstract class SpotAggregateStream implements AutoCloseable {

    private WebSocketSession session;
    private HttpRequest request;
    private String symbol;
    @Inject
    private SpotAggTradeRepository spotAggTradeRepository;

    @OnOpen
    public void onOpen(String symbol, WebSocketSession session, HttpRequest request) {
        this.session = session;
        this.request = request;
        this.symbol = symbol;
        log.info("Open spot aggTrade for {}", symbol);
    }

    @OnMessage
    public void onMessage(AggTradeModel message) {
        Single.fromPublisher(spotAggTradeRepository.save(SpotAggTrade.builder()
                .sportAggTradeId(SportAggTradeId.builder()
                        .aggregateTradeId(message.getAggregateTradeId())
                        .symbol(message.getSymbol())
                        .build())
                .eventTime(message.getEventTime())
                .eventType(message.getEventType())
                .firstTradeId(message.getFirstTradeId())
                .isMarketMaker(message.getIsMarketMaker())
                .lastTradeId(message.getLastTradeId())
                .price(message.getPrice())
                .quantity(message.getQuantity())
                .tradeTime(message.getTradeTime())
                .build()
        )).subscribe();
    }

    @OnClose
    public void onClose() {
        log.info("Close spot aggTrade for {}", symbol);
    }

    public WebSocketSession getSession() {
        return session;
    }

}
