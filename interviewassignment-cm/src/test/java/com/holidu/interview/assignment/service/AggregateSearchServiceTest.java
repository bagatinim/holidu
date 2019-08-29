package com.holidu.interview.assignment.service;

import static org.mockito.Matchers.any;

import com.holidu.interview.assignment.exception.BaseException;
import com.holidu.interview.assignment.exception.DataFetchingException;
import com.holidu.interview.assignment.exception.UnexpectedException;
import com.holidu.interview.assignment.helper.DataFilter;
import com.holidu.interview.assignment.helper.SearchCoordinatesCalculator;
import com.holidu.interview.assignment.helper.UnitConverter;
import com.holidu.interview.assignment.model.external.AggregatedRequest;
import com.holidu.interview.assignment.model.external.ErrorCode;
import com.holidu.interview.assignment.model.internal.Tree;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AggregateSearchServiceTest {

    private AggregateSearchService aggregateSearchService;
    private SearchCoordinatesCalculator coordinatesCalculator;
    private NewYorkDataService newYorkDataService;
    private UnitConverter unitConverter;
    private DataFilter dataFilter;

    @Before
    public void init() {
        this.coordinatesCalculator = new SearchCoordinatesCalculator();
        this.newYorkDataService = Mockito.mock(NewYorkDataService.class);
        this.unitConverter = new UnitConverter();
        this.dataFilter = new DataFilter();

        this.aggregateSearchService = new AggregateSearchService(
            coordinatesCalculator, newYorkDataService, unitConverter, dataFilter);
    }

    @Test
    public void whenGettingAggregatedDataByCommonName_ThenCorrectResultShouldBeReturned() throws BaseException {
        // Given
        AggregatedRequest request = new AggregatedRequest();
        request.setX_sp(new BigDecimal("1027433.053"));
        request.setY_sp(new BigDecimal("226412.3103"));
        request.setSearchRadius(300);
        List<Tree> trees = new ArrayList<>();
        Tree t1 = new Tree("black maple", "1028277.649", "226997.547");
        Tree t2 = new Tree("spruce", "1027833.516", "226872.6697");
        Tree t3 = new Tree("spruce", "1027833.516", "226872.6697");
        Tree t4 = new Tree("spruce", "1027833.516", "226872.6697");
        Tree t5 = new Tree("katsura tree", "1027949.714", "226867.795");
        trees.add(t1);
        trees.add(t2);
        trees.add(t3);
        trees.add(t4);
        trees.add(t5);
        Mockito.when(newYorkDataService.getData(any())).thenReturn(trees);

        // When
        Map<String, Integer> aggregationResult = aggregateSearchService.getAggregatedDataByCommonName(request);

        // Then
        Assert.assertEquals(2, aggregationResult.size());
        Assert.assertTrue(aggregationResult.containsKey("spruce"));
        Assert.assertEquals(3L, aggregationResult.get("spruce").longValue());
        Assert.assertTrue(aggregationResult.containsKey("katsura tree"));
        Assert.assertEquals(1L, aggregationResult.get("katsura tree").longValue());
    }

    @Test(expected = DataFetchingException.class)
    public void whenGettingAggregatedDataThrowsBaseException_ThenTheExceptionShouldBeReturned() throws BaseException {
        // Given
        AggregatedRequest request = new AggregatedRequest();
        request.setX_sp(new BigDecimal("1027433.053"));
        request.setY_sp(new BigDecimal("226412.3103"));
        request.setSearchRadius(300);
        Mockito.when(newYorkDataService.getData(any())).thenThrow(new DataFetchingException(ErrorCode.ERROR_WHILE_FETCHING_DATA));

        // When
        aggregateSearchService.getAggregatedDataByCommonName(request);
    }

    @Test(expected = UnexpectedException.class)
    public void whenGettingAggregatedDataThrowsException_ThenUnexpectedExceptionShouldBeReturned() throws BaseException {
        // Given
        AggregatedRequest request = new AggregatedRequest();
        request.setX_sp(new BigDecimal("1027433.053"));
        request.setY_sp(new BigDecimal("226412.3103"));
        request.setSearchRadius(null);

        // When
        aggregateSearchService.getAggregatedDataByCommonName(request);
    }
}