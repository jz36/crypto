package dev.zykov.repository.spot;

import dev.zykov.entity.future.depth.FutureDepth;
import dev.zykov.entity.future.depth.FutureDepthId;
import dev.zykov.entity.spot.depth.SpotDepth;
import dev.zykov.entity.spot.depth.SpotDepthId;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface SpotDepthRepository extends ReactiveStreamsCrudRepository<SpotDepth, SpotDepthId> {


}
