package dev.zykov.rest;

import dev.zykov.model.DepthRestResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

@Client("https://api.binance.com")
public interface RestClient {

    @Get("/api/v3/depth")
    DepthRestResponse getDepth(@QueryValue String symbol, @QueryValue Integer limit);
}
