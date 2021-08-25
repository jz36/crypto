package dev.zykov.service;

import dev.zykov.entity.future.agg_trade.FutureAggTrade;
import dev.zykov.entity.future.agg_trade.FutureAggTradeId;
import dev.zykov.entity.future.depth.FutureDepth;
import dev.zykov.entity.future.depth.FutureDepthId;
import dev.zykov.entity.spot.agg_trade.SportAggTradeId;
import dev.zykov.entity.spot.agg_trade.SpotAggTrade;
import dev.zykov.entity.spot.depth.SpotDepth;
import dev.zykov.entity.spot.depth.SpotDepthId;
import dev.zykov.model.AggTradeModel;
import dev.zykov.model.DepthResponse;
import dev.zykov.repository.future.FutureAggTradeRepository;
import dev.zykov.repository.future.FutureDepthRepository;
import dev.zykov.repository.spot.SpotAggTradeRepository;
import dev.zykov.repository.spot.SpotDepthRepository;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.concurrent.ConcurrentLinkedQueue;

@Singleton
public class SocketCache {

    private final ConcurrentLinkedQueue<DepthResponse> futureDepth = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<DepthResponse> spotDepth = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<AggTradeModel> futureAggTrade = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<AggTradeModel> spotAggTrade = new ConcurrentLinkedQueue<>();
    @Inject
    private FutureAggTradeRepository futureAggTradeRepository;
    @Inject
    private FutureDepthRepository futureDepthRepository;
    @Inject
    private SpotAggTradeRepository spotAggTradeRepository;
    @Inject
    private SpotDepthRepository spotDepthRepository;

    public void addFutureDepth(DepthResponse depthResponse) {
        futureDepth.add(depthResponse);
    }

    public void addFutureAggTrade(AggTradeModel aggTradeModel) {
        futureAggTrade.add(aggTradeModel);
    }

    public void addSpotDepth(DepthResponse depthResponse) {
        spotDepth.add(depthResponse);
    }

    public void addSpotAggTrade(AggTradeModel aggTradeModel) {
        spotAggTrade.add(aggTradeModel);
    }

    public void saveAndResetFutureDepth() {
        if (futureDepth.size() > 0) {
            var publisher = futureDepthRepository.saveAll(futureDepth.stream()
                    .map(t -> FutureDepth.builder()
                            .futureDepthId(FutureDepthId.builder()
                                    .firstUpdateId(t.getFirstUpdateId())
                                    .symbol(t.getSymbol())
                                    .build())
                            .eventTime(t.getEventTime())
                            .lastUpdateId(t.getLastUpdateId())
                            .lastUpdateIdInLastStream(t.getLastUpdateIdInLastStream())
                            .transactionTime(t.getTransactionTime())
                            .bids(t.getAsks())
                            .asks(t.getAsks())
                            .build())
                    .toList());
            futureDepth.clear();
            Flowable.fromPublisher(publisher).subscribe();
        }
    }

    public void saveAndResetSpotDepth() {
        if (spotDepth.size() > 0) {
            var publisher = spotDepthRepository.saveAll(spotDepth.stream()
                    .map(t -> SpotDepth.builder()
                            .spotDepthId(SpotDepthId.builder()
                                    .firstUpdateId(t.getFirstUpdateId())
                                    .symbol(t.getSymbol())
                                    .build())
                            .eventTime(t.getEventTime())
                            .lastUpdateId(t.getLastUpdateId())
                            .lastUpdateIdInLastStream(t.getLastUpdateIdInLastStream())
                            .transactionTime(t.getTransactionTime())
                            .bids(t.getAsks())
                            .asks(t.getAsks())
                            .build())
                    .toList());
            spotDepth.clear();
            Flowable.fromPublisher(publisher).subscribe();
        }
    }

    public void saveAndResetFutureAggTrade() {
        if (futureAggTrade.size() > 0) {
            var publisher = futureAggTradeRepository.saveAll(futureAggTrade.stream()
                    .map(t -> FutureAggTrade.builder()
                            .futureAggTradeId(FutureAggTradeId.builder()
                                    .aggregateTradeId(t.getAggregateTradeId())
                                    .symbol(t.getSymbol())
                                    .build())
                            .eventTime(t.getEventTime())
                            .eventType(t.getEventType())
                            .firstTradeId(t.getFirstTradeId())
                            .isMarketMaker(t.getIsMarketMaker())
                            .lastTradeId(t.getLastTradeId())
                            .price(t.getPrice())
                            .quantity(t.getQuantity())
                            .tradeTime(t.getTradeTime())
                            .build())
                    .toList());
            futureAggTrade.clear();
            Flowable.fromPublisher(publisher).subscribe();
        }
    }

    public void saveAndResetSpotAggTrade() {
        if (spotAggTrade.size() > 0) {
            var publisher = spotAggTradeRepository.saveAll(spotAggTrade.stream()
                    .map(t -> SpotAggTrade.builder()
                            .sportAggTradeId(SportAggTradeId.builder()
                                    .aggregateTradeId(t.getAggregateTradeId())
                                    .symbol(t.getSymbol())
                                    .build())
                            .eventTime(t.getEventTime())
                            .eventType(t.getEventType())
                            .firstTradeId(t.getFirstTradeId())
                            .isMarketMaker(t.getIsMarketMaker())
                            .lastTradeId(t.getLastTradeId())
                            .price(t.getPrice())
                            .quantity(t.getQuantity())
                            .tradeTime(t.getTradeTime())
                            .build())
                    .toList());
            spotAggTrade.clear();
            Flowable.fromPublisher(publisher).subscribe();
        }
    }


}
