package dev.zykov;

import dev.zykov.model.SymbolDepthCache;
import dev.zykov.service.DepthCache;
import dev.zykov.socket.MainClientSocket;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.RequiredArgsConstructor;

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
    public SymbolDepthCache getDepthCache() {
        return depthCache.getDepthCache().get("bnbbtc");
    }

}
