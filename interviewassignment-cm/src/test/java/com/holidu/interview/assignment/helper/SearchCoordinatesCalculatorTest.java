package com.holidu.interview.assignment.helper;

import com.holidu.interview.assignment.model.internal.SearchCoordinateBoundaries;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SearchCoordinatesCalculatorTest {

    private SearchCoordinatesCalculator searchCoordinatesCalculator;

    @Before
    public void setUp() {
        this.searchCoordinatesCalculator = new SearchCoordinatesCalculator();
    }

    @Test
    public void whenGettingSearchCoordinates_ThenCorrectResultShouldBeReturned() {
        // Given
        BigDecimal xCenterPoint = new BigDecimal("1027433.053");
        BigDecimal yCenterPoint = new BigDecimal("226412.3103");
        BigDecimal radius = new BigDecimal("984.24");

        // When
        SearchCoordinateBoundaries boundaries = searchCoordinatesCalculator
            .getSearchCoordinates(xCenterPoint, yCenterPoint, radius);

        // Then
        Assert.assertEquals("1028417.293", boundaries.getXUpperBoundary().toString());
        Assert.assertEquals("1026448.813", boundaries.getXLowerBoundary().toString());
        Assert.assertEquals("227396.5503", boundaries.getYUpperBoundary().toString());
        Assert.assertEquals("225428.0703", boundaries.getYLowerBoundary().toString());
    }
}