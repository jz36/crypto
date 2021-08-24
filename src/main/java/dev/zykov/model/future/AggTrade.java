package dev.zykov.model.future;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.MappedEntity;
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
    @JsonProperty("e")
    private String eventType;

    @Column(name = "event_time")
    @JsonProperty("E")
    private Long eventTime;

    @JsonProperty("p")
    @Column(name = "price")
    private Float price;

    @JsonProperty("q")
    @Column(name = "quantity")
    private Float quantity;

    @JsonProperty("f")
    @Column(name = "first_trade_id")
    private Long firstTradeId;

    @JsonProperty("l")
    @Column(name = "last_trade_id")
    private Long lastTradeId;

    @JsonProperty("T")
    @Column(name = "trade_time")
    private Long tradeTime;

    @JsonProperty("m")
    @Column(name = "market_maker")
    private Boolean isMarketMaker;


}
