package dev.zykov.enity.future;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Introspected
@Table(name = "agg_trade")
@Entity
public class AggTrade implements Serializable {

    @EmbeddedId
    @JsonUnwrapped
    private AggTradeId aggTradeId;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "event_time")
    private Long eventTime;

    @Column(name = "price")
    private Float price;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "first_trade_id")
    private Long firstTradeId;

    @Column(name = "last_trade_id")
    private Long lastTradeId;

    @Column(name = "trade_time")
    private Long tradeTime;

    @Column(name = "market_maker")
    private Boolean isMarketMaker;


}
