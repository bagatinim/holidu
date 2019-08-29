package com.holidu.interview.assignment.model.internal;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Coordinates {

    private BigDecimal x;
    private BigDecimal y;

    public Coordinates(String x, String y) {
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
    }

}
