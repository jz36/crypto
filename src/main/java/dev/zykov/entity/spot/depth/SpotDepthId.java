package dev.zykov.entity.spot.depth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpotDepthId implements Serializable {

    @Column(name = "first_update_id")
    private Long firstUpdateId;

    @Column(name = "symbol")
    private String symbol;
}
