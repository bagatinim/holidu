package com.holidu.interview.assignment.model.external;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AggregatedRequest {

    @NotNull
    private BigDecimal x_sp;

    @NotNull
    private BigDecimal y_sp;

    @NotNull
    private Integer searchRadius;

}
