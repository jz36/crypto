package dev.zykov.rest;

import dev.zykov.model.DepthRestResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import java.util.concurrent.CompletableFuture;

@Client("https://api.binance.com")
public interface SpotRestClient {

    @Get("/api/v3/depth")
    CompletableFuture<DepthRestResponse> getDepth(@QueryValue String symbol, @QueryValue Integer limit);
}
