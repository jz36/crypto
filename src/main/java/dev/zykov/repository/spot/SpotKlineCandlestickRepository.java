package dev.zykov.repository.spot;

import dev.zykov.entity.spot.kline_candlestick.SpotKlineCandlestick;
import dev.zykov.entity.spot.kline_candlestick.SpotKlineCandlestickId;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SpotKlineCandlestickRepository extends ReactiveStreamsCrudRepository<SpotKlineCandlestick, SpotKlineCandlestickId> {
}
