package dev.zykov.entity.future.kline_candlestick;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class FutureKlineCandlestickId implements Serializable {

    @Column(name = "event_time")
    private Long eventTime;

    @Column(name = "symbol")
    private String symbol;
}
