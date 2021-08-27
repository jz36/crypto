package dev.zykov.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class KlineCandlestickSubModel {

    @JsonAlias("t")
    @JsonProperty("klineStartTime")
    private Long klineStartTime;

    @JsonAlias("T")
    @JsonProperty("klineCloseTime")
    private Long klineCloseTime;

    @JsonAlias("s")
    @JsonProperty("symbol")
    private String symbol;

    @JsonAlias("i")
    @JsonProperty("interval")
    private String interval;

    @JsonAlias("f")
    @JsonProperty("firstTradeId")
    private Long firstTradeId;

    @JsonAlias("L")
    @JsonProperty("lastTadeId")
    private Long lastTadeId;

    @JsonAlias("o")
    @JsonProperty("openPrice")
    private BigDecimal openPrice;

    @JsonAlias("c")
    @JsonProperty("closePrice")
    private BigDecimal closePrice;

    @JsonAlias("h")
    @JsonProperty("highPrice")
    private BigDecimal highPrice;

    @JsonAlias("l")
    @JsonProperty("lowPrice")
    private BigDecimal lowPrice;

    @JsonAlias("v")
    @JsonProperty("baseAssetVolume")
    private BigDecimal baseAssetVolume;

    @JsonAlias("n")
    @JsonProperty("numberOfTrades")
    private Integer numberOfTrades;

    @JsonAlias("x")
    @JsonProperty("isKlineClosed")
    private Boolean isKlineClosed;

    @JsonAlias("q")
    @JsonProperty("quoteAssetVolume")
    private BigDecimal quoteAssetVolume;

    @JsonAlias("V")
    @JsonProperty("takerBuyBaseAssetVolume")
    private BigDecimal takerBuyBaseAssetVolume;

    @JsonAlias("Q")
    @JsonProperty("takerBuyQuoteAssetVolume")
    private BigDecimal takerBuyQuoteAssetVolume;
}
