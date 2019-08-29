package com.holidu.interview.assignment.helper;

import com.holidu.interview.assignment.service.AggregateSearchService;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UnitConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateSearchService.class);
    private static final BigDecimal METERS_TO_FEET = new BigDecimal("3.2808");

    /**
     * Converts meters to feet.
     */
    public BigDecimal metersToFeet(Integer meters) {
        BigDecimal feet = METERS_TO_FEET.multiply(new BigDecimal(meters));
        LOGGER.info("{} meters converted to {} feet", meters, feet);
        return feet;
    }

}
