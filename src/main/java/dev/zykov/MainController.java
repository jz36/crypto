package dev.zykov;

import dev.zykov.model.SymbolDepthCache;
import dev.zykov.service.DepthCache;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.RequiredArgsConstructor;

import java.util.Enumeration;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final DepthCache depthCache;

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

}
