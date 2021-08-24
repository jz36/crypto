package dev.zykov.repository;

import dev.zykov.model.future.AggTrade;
import dev.zykov.model.future.AggTradeId;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Flux;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface AggTradeRepository extends ReactiveStreamsCrudRepository<AggTrade, AggTradeId> {

    @NonNull
    @Override
    Flux<AggTrade> findAll();

}
