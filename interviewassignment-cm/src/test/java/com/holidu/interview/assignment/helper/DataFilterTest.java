package com.holidu.interview.assignment.helper;

import com.holidu.interview.assignment.model.internal.Coordinates;
import com.holidu.interview.assignment.model.internal.Tree;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataFilterTest {

    private DataFilter dataFilter;

    @Before
    public void setUp() {
        this.dataFilter = new DataFilter();
    }

    @Test
    public void whenCalculatingDistanceBetweenPoints_TheCorrectResultShouldBeReturned() {
        // Given
        Coordinates p1 = new Coordinates("1027433.053", "226412.3103");
        Coordinates p2 = new Coordinates("1028277.649", "226997.547");
        BigDecimal expectedDistance = new BigDecimal("1027.5428936267772");

        // When
        BigDecimal distance = dataFilter.distanceBetweenPoints(p1, p2);

        // Then
        Assert.assertEquals(expectedDistance, distance);
    }

    @Test
    public void whenFilteringTreesByRadius_TheCorrectResultShouldBeReturned() {
        // Given
        List<Tree> trees = new ArrayList<>();
        Tree t1 = new Tree("black maple", "1028277.649", "226997.547");
        Tree t2 = new Tree("spruce", "1027833.516", "226872.6697");
        Tree t3 = new Tree("katsura tree", "1027949.714", "226867.795");
        trees.add(t1);
        trees.add(t2);
        trees.add(t3);
        BigDecimal radius = new BigDecimal("984.24");
        Coordinates centerPoint = new Coordinates("1027433.053", "226412.3103");

        // When
        List<Tree> filteredTrees = dataFilter.filterTreesByRadius(trees, radius, centerPoint);

        // Then
        Assert.assertEquals(2, filteredTrees.size());
    }
}