package dev.zykov;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zykov.entity.future.depth.FutureDepth;
import dev.zykov.entity.future.depth.FutureDepthId;
import dev.zykov.model.DepthResponse;
import dev.zykov.model.SymbolDepthCache;
import dev.zykov.repository.future.FutureDepthRepository;
import dev.zykov.service.DepthCache;
import io.micronaut.http.annotation.*;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final DepthCache depthCache;
    private final FutureDepthRepository futureDepthRepository;
    private final ObjectMapper objectMapper;

    @Get("/depth/spot")
    public Enumeration<String> getSpotSymbols() {
        return depthCache.getSpotDepthCache()
                .keys();
    }

    @Get("/depth/spot/{symbol}")
    public SymbolDepthCache getSpotDepthCache(@PathVariable String symbol) {
        return depthCache.getSpotDepthCache()
                .get(symbol);
    }

    @Get("/depth/future")
    public Enumeration<String> getFutureSymbols() {
        return depthCache.getFutureDepthCache()
                .keys();
    }

    @Get("/depth/future/{symbol}")
    public SymbolDepthCache getFutureDepthCache(@PathVariable String symbol) {
        return depthCache.getFutureDepthCache()
                .get(symbol);
    }

    @Post("/test")
    public Single<FutureDepth> save(@Body DepthResponse message) throws JsonProcessingException {
        return Single.fromPublisher(futureDepthRepository.save(FutureDepth.builder()
                .futureDepthId(FutureDepthId.builder()
                        .firstUpdateId(message.getFirstUpdateId())
                        .symbol(message.getSymbol())
                        .build())
                .eventTime(message.getEventTime())
                .lastUpdateId(message.getLastUpdateId())
                .lastUpdateIdInLastStream(message.getLastUpdateIdInLastStream())
                .transactionTime(message.getTransactionTime())
                .bids(message.getBids())
                .asks(message.getAsks())
                .build()));
    }

}
