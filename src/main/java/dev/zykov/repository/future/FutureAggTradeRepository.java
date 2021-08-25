package dev.zykov.repository.future;

import dev.zykov.entity.future.agg_trade.FutureAggTrade;
import dev.zykov.entity.future.agg_trade.FutureAggTradeId;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Flux;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface FutureAggTradeRepository extends ReactiveStreamsCrudRepository<FutureAggTrade, FutureAggTradeId> {

    @NonNull
    @Override
    Flux<FutureAggTrade> findAll();

}
