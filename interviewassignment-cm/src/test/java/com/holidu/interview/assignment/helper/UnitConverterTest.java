package com.holidu.interview.assignment.helper;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnitConverterTest {

    private UnitConverter unitConverter;

    @Before
    public void setUp() {
        this.unitConverter = new UnitConverter();
    }

    @Test
    public void whenConvertingMetersToFeet_ThenTheCorrectResultShouldBeReturned() {
        // When
        BigDecimal feet = unitConverter.metersToFeet(300);

        // Then
        Assert.assertEquals(new BigDecimal("984.2400"), feet);
    }


    @Test
    public void whenConvertingOneMeterToFeet_ThenTheCorrectResultShouldBeReturned() {
        // When
        BigDecimal feet = unitConverter.metersToFeet(1);

        // Then
        Assert.assertEquals(new BigDecimal("3.2808"), feet);
    }
}