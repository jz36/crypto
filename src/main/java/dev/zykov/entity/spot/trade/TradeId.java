package dev.zykov.entity.spot.trade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Embeddable
public class TradeId implements Serializable {

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "trade_id")
    private Long tradeId;

}
