package com.holidu.interview.assignment.service;

import com.holidu.interview.assignment.exception.BaseException;
import com.holidu.interview.assignment.exception.UnexpectedException;
import com.holidu.interview.assignment.helper.DataFilter;
import com.holidu.interview.assignment.helper.SearchCoordinatesCalculator;
import com.holidu.interview.assignment.helper.UnitConverter;
import com.holidu.interview.assignment.model.external.AggregatedRequest;
import com.holidu.interview.assignment.model.external.ErrorCode;
import com.holidu.interview.assignment.model.internal.Coordinates;
import com.holidu.interview.assignment.model.internal.SearchCoordinateBoundaries;
import com.holidu.interview.assignment.model.internal.Tree;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AggregateSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateSearchService.class);

    private static final String NULL_NAME = "COMMON NAME NOT INFORMED";

    private SearchCoordinatesCalculator coordinatesCalculator;
    private NewYorkDataService newYorkDataService;
    private UnitConverter unitConverter;
    private DataFilter dataFilter;

    @Autowired
    public AggregateSearchService(SearchCoordinatesCalculator coordinatesCalculator,
                                  NewYorkDataService newYorkDataService,
                                  UnitConverter unitConverter,
                                  DataFilter dataFilter) {
        this.coordinatesCalculator = coordinatesCalculator;
        this.newYorkDataService = newYorkDataService;
        this.unitConverter = unitConverter;
        this.dataFilter = dataFilter;
    }

    public Map<String, Integer> getAggregatedDataByCommonName(AggregatedRequest request) throws BaseException {
        try {
            LOGGER.info("Calculating search boundaries for the request: {}", request);
            BigDecimal radiusInFeet = unitConverter.metersToFeet(request.getSearchRadius());
            SearchCoordinateBoundaries searchBoundaries = coordinatesCalculator
                .getSearchCoordinates(request.getX_sp(), request.getY_sp(), radiusInFeet);

            LOGGER.info("Gathering New York data with the following boundaries: {}", searchBoundaries);
            List<Tree> trees = newYorkDataService.getData(searchBoundaries);

            LOGGER.info("Number of trees returned from the external service: {}", trees.size());
            dataFilter.filterTreesByRadius(trees, radiusInFeet, new Coordinates(request.getX_sp(), request.getY_sp()));
            LOGGER.info("Trees within the given circumference, as set by the central point and the radius: {}", trees.size());

            Map<String, Integer> treesAggregation = new HashMap<>();
            trees.stream()
                .forEach(tree ->
                    treesAggregation.merge(tree.getSpc_common() == null ? NULL_NAME : tree.getSpc_common(), 1, Integer::sum));

            LOGGER.info("Aggregation by Common Name found {} types of trees.", treesAggregation.size());
            return treesAggregation;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected exception while aggregating data.", e);
            throw new UnexpectedException(ErrorCode.UNEXPECTED_ERROR);
        }
    }

}
