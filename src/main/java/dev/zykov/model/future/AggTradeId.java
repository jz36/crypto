package dev.zykov.model.future;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AggTradeId implements Serializable{

    @JsonProperty("a")
    @Column(name = "aggregate_trade_id")
    private Long aggregateTradeId;
    @JsonProperty("s")
    @Column(name = "symbol")
    private String symbol;
}
