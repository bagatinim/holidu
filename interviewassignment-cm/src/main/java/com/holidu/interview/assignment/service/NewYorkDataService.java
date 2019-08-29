package com.holidu.interview.assignment.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.holidu.interview.assignment.exception.DataFetchingException;
import com.holidu.interview.assignment.exception.EmptyResultException;
import com.holidu.interview.assignment.model.external.ErrorCode;
import com.holidu.interview.assignment.model.internal.SearchCoordinateBoundaries;
import com.holidu.interview.assignment.model.internal.Tree;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NewYorkDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewYorkDataService.class);

    private static final TypeToken<List<Tree>> TREE_DATA = new TypeToken<List<Tree>>() {};
    private static final String BASE_URL = "https://data.cityofnewyork.us/resource/uvpi-gqnh.json?$select=x_sp,y_sp,spc_common";
    private static final String FILTER_PARAMETERS = "&$where=x_sp <= %s AND x_sp >= %s AND y_sp <= %s AND y_sp >= %s";

    private RestTemplate restTemplate;
    private Gson gson;

    @Autowired
    public NewYorkDataService(RestTemplate restTemplate,
                              Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    public List<Tree> getData(SearchCoordinateBoundaries boundaries) throws DataFetchingException, EmptyResultException {
        try {
            String filter = String.format(FILTER_PARAMETERS,
                boundaries.getXUpperBoundary(), boundaries.getXLowerBoundary(),
                boundaries.getYUpperBoundary(), boundaries.getYLowerBoundary());

            LOGGER.info("Requesting data with the following URL: {}", BASE_URL + filter);
            ResponseEntity response = restTemplate.exchange(BASE_URL + filter, HttpMethod.GET, null, String.class);

            if (!response.getStatusCode().equals(HttpStatus.OK) || response.getBody() == null) {
                throw new EmptyResultException(ErrorCode.EMPTY_RESULT);
            }

            return gson.fromJson(response.getBody().toString(), TREE_DATA.getType());
        } catch (RestClientException e) {
            LOGGER.error("Error while fetching New York tree data.", e);
            throw new DataFetchingException(ErrorCode.ERROR_WHILE_FETCHING_DATA);
        }
    }

}
