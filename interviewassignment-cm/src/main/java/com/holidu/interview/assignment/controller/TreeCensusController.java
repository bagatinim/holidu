package com.holidu.interview.assignment.controller;

import com.holidu.interview.assignment.exception.BaseException;
import com.holidu.interview.assignment.model.external.AggregatedRequest;
import com.holidu.interview.assignment.service.AggregateSearchService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/treeCensus")
public class TreeCensusController {

    private AggregateSearchService aggregateSearchService;

    @Autowired
    public TreeCensusController(AggregateSearchService aggregateSearchService) {
        this.aggregateSearchService = aggregateSearchService;
    }

    @PostMapping("v1.0/aggregate/commonName")
    public ResponseEntity aggregateTreeDataByCommonName(@RequestBody @Valid AggregatedRequest requestModel) throws BaseException {
        return ResponseEntity.ok(aggregateSearchService.getAggregatedDataByCommonName(requestModel));
    }

}
