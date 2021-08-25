package dev.zykov.repository.spot;

import dev.zykov.entity.spot.agg_trade.SportAggTradeId;
import dev.zykov.entity.spot.agg_trade.SpotAggTrade;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Flux;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SpotAggTradeRepository extends ReactiveStreamsCrudRepository<SpotAggTrade, SportAggTradeId> {

    @NonNull
    @Override
    Flux<SpotAggTrade> findAll();

}
