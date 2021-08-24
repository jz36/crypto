package dev.zykov.enity.future;

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

    @Column(name = "aggregate_trade_id")
    private Long aggregateTradeId;

    @Column(name = "symbol")
    private String symbol;
}
