package com.holidu.interview.assignment.controller;

import com.holidu.interview.assignment.exception.BaseException;
import com.holidu.interview.assignment.model.external.AggregatedRequest;
import com.holidu.interview.assignment.service.AggregateSearchService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TreeCensusControllerTest {

    private AggregateSearchService aggregateSearchService;
    private TreeCensusController treeCensusController;

    @Before
    public void setUp() {
        this.aggregateSearchService = Mockito.mock(AggregateSearchService.class);
        this.treeCensusController = new TreeCensusController(aggregateSearchService);
    }

    @Test
    public void whenAggregatingTreeDataByCommonName_TheCorrectResultShouldBeReturned() throws BaseException {
        // Giver
        AggregatedRequest request = new AggregatedRequest();
        request.setX_sp(new BigDecimal("1027433.053"));
        request.setY_sp(new BigDecimal("226412.3103"));
        request.setSearchRadius(300);
        Map<String, Integer> aggregation = new HashMap<String, Integer>() {{ put("arborvitae", 3); }};
        Mockito.when(aggregateSearchService.getAggregatedDataByCommonName(request)).thenReturn(aggregation);

        // When
        ResponseEntity response = treeCensusController.aggregateTreeDataByCommonName(request);

        // Then
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Map<String, Integer> aggregationResponse = (Map<String, Integer>) response.getBody();
        Assert.assertTrue(aggregationResponse.containsKey("arborvitae"));
        Assert.assertEquals(3L, aggregationResponse.get("arborvitae").longValue());
    }
}