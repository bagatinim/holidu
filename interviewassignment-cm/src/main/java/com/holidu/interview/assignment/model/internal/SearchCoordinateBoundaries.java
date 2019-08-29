package com.holidu.interview.assignment.model.internal;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchCoordinateBoundaries {

    private BigDecimal xUpperBoundary;
    private BigDecimal xLowerBoundary;
    private BigDecimal yUpperBoundary;
    private BigDecimal yLowerBoundary;

}
