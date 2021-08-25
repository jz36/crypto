package dev.zykov.entity.spot.trade;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(schema = "spot", name = "trade")
public class Trade {

    @Column(name = "event_time")
    private Long eventTime;

    @EmbeddedId
    @JsonUnwrapped
    private TradeId tradeId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "buyer")
    private Long buyer;

    @Column(name = "seller")
    private Long seller;

    @Column(name = "trade_time")
    private Long tradeTime;

    @Column(name = "is_buyer_the_market_maker")
    private Boolean isBuyerTheMarketMaker;



}
