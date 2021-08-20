package dev.zykov.socket;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava2.http.client.websockets.RxWebSocketClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;


@Singleton
public class MainClientSocket {

    @Inject
    @Client("https://stream.binance.com:9443")
    RxWebSocketClient webSocketClient;

    public void test() {
        var depthStream = webSocketClient.connect(DepthStream.class, "/ws/bnbbtc@depth@100ms")
                .blockingFirst();
    }

}
