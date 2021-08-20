package dev.zykov;

import dev.zykov.model.SymbolDepthCache;
import dev.zykov.service.DepthCache;
import dev.zykov.socket.MainClientSocket;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.RequiredArgsConstructor;

import java.util.Enumeration;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainClientSocket mainClientSocket;
    private final DepthCache depthCache;

    @Get
    public void test() {
        mainClientSocket.test();
    }

    @Get("/depth")
    public Enumeration<String> getSymbols() {
        return depthCache.getDepthCache()
                .keys();
    }

    @Get("/depth/{symbol}")
    public SymbolDepthCache getDepthCache(@PathVariable String symbol) {
        return depthCache.getDepthCache()
                .get(symbol);
    }

}
