package dev.zykov.entity.future.kline_candlestick;

import dev.zykov.model.KlineCandlestickSubModel;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "future", name = "kline_candlestick")
public class FutureKlineCandlestick {

    @EmbeddedId
    private FutureKlineCandlestickId futureKlineCandlestickId;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @TypeDef(type = DataType.JSON)
    @Column(name = "kline_candlestick")
    private KlineCandlestickSubModel klineCandlestickSubModel;
}
