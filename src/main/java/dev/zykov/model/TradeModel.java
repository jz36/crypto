package dev.zykov.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Introspected
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeModel {

    @JsonProperty("E")
    private Long eventTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("t")
    private Long tradeId;

    @JsonProperty("p")
    private BigDecimal price;

    @JsonProperty("q")
    private BigDecimal quantity;

    @JsonProperty("b")
    private Long buyer;

    @JsonProperty("a")
    private Long seller;

    @JsonProperty("T")
    private Long tradeTime;

    @JsonProperty("m")
    private Boolean isBuyerTheMarketMaker;


}
