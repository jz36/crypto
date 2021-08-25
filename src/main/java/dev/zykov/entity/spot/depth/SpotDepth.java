package dev.zykov.entity.spot.depth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.micronaut.core.annotation.Introspected;
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
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Introspected
@Table(name = "depth", schema = "spot")
@Entity
public class SpotDepth {

    @Column(name = "event_time")
    private Long eventTime;

    @Column(name = "transaction_time")
    private Long transactionTime;

    @JsonUnwrapped
    @EmbeddedId
    private SpotDepthId spotDepthId;

    @Column(name = "last_update_id")
    private Long lastUpdateId;

    @Column(name = "last_update_id_in_last_stream")
    private Long lastUpdateIdInLastStream;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @TypeDef(type = DataType.JSON)
    @Column(name = "bids")
    private List<List<BigDecimal>> bids;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @TypeDef(type = DataType.JSON)
    @JsonProperty("a")
    private List<List<BigDecimal>> asks;
}
