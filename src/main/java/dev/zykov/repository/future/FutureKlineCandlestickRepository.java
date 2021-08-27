package dev.zykov.repository.future;

import dev.zykov.entity.future.kline_candlestick.FutureKlineCandlestick;
import dev.zykov.entity.future.kline_candlestick.FutureKlineCandlestickId;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface FutureKlineCandlestickRepository extends ReactiveStreamsCrudRepository<FutureKlineCandlestick, FutureKlineCandlestickId> {
}
