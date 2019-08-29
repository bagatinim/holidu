package com.holidu.interview.assignment.helper;

import com.holidu.interview.assignment.model.internal.Coordinates;
import com.holidu.interview.assignment.model.internal.Tree;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DataFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataFilter.class);

    /**
     * Given a list of trees laid out in a X/Y axis, filters out the ones outside the diameter set by the central point and the
     * radius.
     */
    public List<Tree> filterTreesByRadius(List<Tree> trees, BigDecimal radiusInFeet, Coordinates centerPoint) {
        trees.removeIf(tree ->
            radiusInFeet.compareTo(
                distanceBetweenPoints(centerPoint, new Coordinates(tree.getX_sp(), tree.getY_sp()))) < 0
        );

        return trees;
    }

    /**
     * Calculates the distance between two coordinates in an X/Y axis.
     */
    public BigDecimal distanceBetweenPoints(Coordinates p1, Coordinates p2) {
        BigDecimal distance = BigDecimal.valueOf(
            Math.hypot(p1.getX().subtract(p2.getX()).doubleValue(), p1.getY().subtract(p2.getY()).doubleValue()));

        LOGGER.info("The distance between {} and {} is {}", p1, p2, distance);
        return distance;
    }

}
