package dev.zykov.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Introspected
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepthRestResponse {

    private Long lastUpdateId;
    private List<List<BigDecimal>> bids;
    private List<List<BigDecimal>> asks;
}
