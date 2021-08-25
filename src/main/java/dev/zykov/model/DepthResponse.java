package dev.zykov.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
@Builder
public class DepthResponse {

    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private Long eventTime;

    @JsonProperty("T")
    private Long transactionTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("U")
    private Long firstUpdateId;

    @JsonProperty("u")
    private Long lastUpdateId;

    @JsonProperty("pu")
    private Long lastUpdateIdInLastStream;

//    @JsonDeserialize(as = String.class, using = MyJsonSerializer.class)
    @JsonProperty("b")
    private List<List<BigDecimal>> bids;

//    @JsonDeserialize(as = String.class, using = MyJsonSerializer.class)
    @JsonProperty("a")
    private List<List<BigDecimal>> asks;
}
