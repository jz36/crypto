package dev.zykov.repository.future;

import dev.zykov.entity.future.depth.FutureDepth;
import dev.zykov.entity.future.depth.FutureDepthId;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface FutureDepthRepository extends ReactiveStreamsCrudRepository<FutureDepth, FutureDepthId> {


}
