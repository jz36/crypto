package dev.zykov.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

@Data
@Introspected
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AggTradeModel {

    @JsonProperty("a")
    private Long aggregateTradeId;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private Long eventTime;

    @JsonProperty("p")
    private Float price;

    @JsonProperty("q")
    private Float quantity;

    @JsonProperty("f")
    private Long firstTradeId;

    @JsonProperty("l")
    private Long lastTradeId;

    @JsonProperty("T")
    private Long tradeTime;

    @JsonProperty("m")
    private Boolean isMarketMaker;

}
