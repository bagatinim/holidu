package com.holidu.interview.assignment.helper;

import com.holidu.interview.assignment.model.internal.SearchCoordinateBoundaries;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class SearchCoordinatesCalculator {

    /**
     * Given a central point defined by a pair of coordinates X and Y, this method uses the radius to calculate the search
     * boundaries.
     */
    public SearchCoordinateBoundaries getSearchCoordinates(BigDecimal xCenterPoint,
                                                           BigDecimal yCenterPoint,
                                                           BigDecimal radiusInFeet) {
        SearchCoordinateBoundaries boundaries = new SearchCoordinateBoundaries();

        boundaries.setXLowerBoundary(xCenterPoint.subtract(radiusInFeet));
        boundaries.setXUpperBoundary(xCenterPoint.add(radiusInFeet));
        boundaries.setYLowerBoundary(yCenterPoint.subtract(radiusInFeet));
        boundaries.setYUpperBoundary(yCenterPoint.add(radiusInFeet));

        return boundaries;
    }

}
