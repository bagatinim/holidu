package com.holidu.interview.assignment.service;

import com.google.gson.Gson;
import com.holidu.interview.assignment.exception.DataFetchingException;
import com.holidu.interview.assignment.exception.EmptyResultException;
import com.holidu.interview.assignment.model.internal.SearchCoordinateBoundaries;
import com.holidu.interview.assignment.model.internal.Tree;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class NewYorkDataServiceTest {

    private NewYorkDataService newYorkDataService;
    private RestTemplate restTemplate;
    private Gson gson;

    @Before
    public void init() {
        this.restTemplate = Mockito.mock(RestTemplate.class);
        this.gson = new Gson();
        this.newYorkDataService = new NewYorkDataService(restTemplate, gson);
    }

    @Test
    public void whenGettingData_ThenCorrectResultShouldBeReturned() throws EmptyResultException, DataFetchingException {
        // Given
        String expectedUrl = "https://data.cityofnewyork.us/resource/uvpi-gqnh.json?$select=x_sp,y_sp,spc_common&$where=x_sp <= 1028417.2930 AND x_sp >= 1026448.8130 AND y_sp <= 227396.5503 AND y_sp >= 225428.0703";
        SearchCoordinateBoundaries boundaries = new SearchCoordinateBoundaries(
            new BigDecimal("1028417.2930"),
            new BigDecimal("1026448.8130"),
            new BigDecimal("227396.5503"),
            new BigDecimal("225428.0703"));
        ResponseEntity response = ResponseEntity.ok("[{\"spc_common\":\"Sophora\", \"x_sp\":\"1234.5\", \"y_sp\":\"5432.1\"}]");
        Mockito.when(restTemplate.exchange(expectedUrl, HttpMethod.GET, null, String.class)).thenReturn(response);

        // When
        List<Tree> data = newYorkDataService.getData(boundaries);

        // Then
        Assert.assertEquals(1, data.size());
        Assert.assertEquals("Sophora", data.get(0).getSpc_common());
        Assert.assertEquals("1234.5", data.get(0).getX_sp());
        Assert.assertEquals("5432.1", data.get(0).getY_sp());
    }

    @Test(expected = EmptyResultException.class)
    public void whenGettingDataAndTheResponseIsNotOk_ThenEmptyResultExceptionShouldBeThrown()
        throws EmptyResultException, DataFetchingException {
        // Given
        String expectedUrl = "https://data.cityofnewyork.us/resource/uvpi-gqnh.json?$select=x_sp,y_sp,spc_common&$where=x_sp <= 1028417.2930 AND x_sp >= 1026448.8130 AND y_sp <= 227396.5503 AND y_sp >= 225428.0703";
        SearchCoordinateBoundaries boundaries = new SearchCoordinateBoundaries(
            new BigDecimal("1028417.2930"),
            new BigDecimal("1026448.8130"),
            new BigDecimal("227396.5503"),
            new BigDecimal("225428.0703"));
        ResponseEntity response = ResponseEntity.badRequest().build();
        Mockito.when(restTemplate.exchange(expectedUrl, HttpMethod.GET, null, String.class)).thenReturn(response);

        // When
        newYorkDataService.getData(boundaries);
    }

    @Test(expected = EmptyResultException.class)
    public void whenGettingDataAndTheResponseBodyIsEmpty_ThenEmptyResultExceptionShouldBeThrown()
        throws EmptyResultException, DataFetchingException {
        // Given
        String expectedUrl = "https://data.cityofnewyork.us/resource/uvpi-gqnh.json?$select=x_sp,y_sp,spc_common&$where=x_sp <= 1028417.2930 AND x_sp >= 1026448.8130 AND y_sp <= 227396.5503 AND y_sp >= 225428.0703";
        SearchCoordinateBoundaries boundaries = new SearchCoordinateBoundaries(
            new BigDecimal("1028417.2930"),
            new BigDecimal("1026448.8130"),
            new BigDecimal("227396.5503"),
            new BigDecimal("225428.0703"));
        ResponseEntity response = ResponseEntity.ok(null);
        Mockito.when(restTemplate.exchange(expectedUrl, HttpMethod.GET, null, String.class)).thenReturn(response);

        // When
        newYorkDataService.getData(boundaries);
    }

    @Test(expected = DataFetchingException.class)
    public void whenGettingDataAndRestClientExceptionIsThrown_ThenDataFetchingExceptionShouldBeThrown()
        throws EmptyResultException, DataFetchingException {
        // Given
        String expectedUrl = "https://data.cityofnewyork.us/resource/uvpi-gqnh.json?$select=x_sp,y_sp,spc_common&$where=x_sp <= 1028417.2930 AND x_sp >= 1026448.8130 AND y_sp <= 227396.5503 AND y_sp >= 225428.0703";
        SearchCoordinateBoundaries boundaries = new SearchCoordinateBoundaries(
            new BigDecimal("1028417.2930"),
            new BigDecimal("1026448.8130"),
            new BigDecimal("227396.5503"),
            new BigDecimal("225428.0703"));
        Mockito.when(restTemplate.exchange(expectedUrl, HttpMethod.GET, null, String.class))
            .thenThrow(new RestClientException(""));

        // When
        newYorkDataService.getData(boundaries);
    }

}