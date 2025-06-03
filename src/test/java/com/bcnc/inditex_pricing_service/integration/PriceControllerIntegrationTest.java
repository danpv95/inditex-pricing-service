package com.bcnc.inditex_pricing_service.integration;

import com.bcnc.InditexPricingServiceApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = InditexPricingServiceApplication.class)
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/v1/prices";

    @Test
    @DisplayName("Test 1: 14-Jun 10:00 - priceList 1, price 35.50 (priority 0 - base rate)")
    void testCase1_RequiredTest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 2: 14-Jun 16:00 - priceList 2, price 25.45 (priority 1 - promotional rate)")
    void testCase2_RequiredTest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 3: 14-Jun 21:00 - priceList 1, price 35.50 (back to base rate after promo)")
    void testCase3_RequiredTest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 4: 15-Jun 10:00 - priceList 3, price 30.50 (second day morning promo)")
    void testCase4_RequiredTest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-15T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-15T11:00:00"))
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 5: 16-Jun 21:00 - priceList 4, price 38.95 (premium evening rate)")
    void testCase5_RequiredTest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-16T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 6: 14-Jun 15:00:00 - priceList 2, price 25.45 (exact start of promo period)")
    void testCase6_BoundaryStart() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T15:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Test 7: 14-Jun 18:30:00 - priceList 2, price 25.45 (exact end of promo period)")
    void testCase7_BoundaryEnd() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T18:30:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Test 8: 14-Jun 14:59:59 - priceList 1, price 35.50 (1 second before promo)")
    void testCase8_OneSecondBefore() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T14:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 9: 14-Jun 18:30:01 - priceList 1, price 35.50 (1 second after promo)")
    void testCase9_OneSecondAfter() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T18:30:01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 10: 15-Jun 16:00:00 - priceList 4, price 38.95 (exact start of premium period)")
    void testCase10_PremiumStart() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-15T16:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    @DisplayName("Test 11: 15-Jun 11:00:00 - priceList 3, price 30.50 (exact end of morning promo)")
    void testCase11_MorningPromoEnd() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-15T11:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    @DisplayName("Test 12: 15-Jun 12:00:00 - priceList 1, price 35.50 (gap between promos)")
    void testCase12_BetweenPromos() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-15T12:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 13: 31-Dec 23:59:59 - priceList 4, price 38.95 (last second of year)")
    void testCase13_YearEnd() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }

    // ========== ERROR CASES ==========

    @Test
    @DisplayName("Test 14: 13-Jun 23:59:59 - 404 Not Found (before any price period)")
    void testCase14_BeforeAllPeriods() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-13T23:59:59"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("NO_APPLICABLE_PRICE"))
                .andExpect(jsonPath("$.message").value("No applicable price found for product 35455 and brand 1"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("Test 15: 01-Jan-2021 00:00:00 - 404 Not Found (after all price periods)")
    void testCase15_AfterAllPeriods() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2021-01-01T00:00:00"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("NO_APPLICABLE_PRICE"))
                .andExpect(jsonPath("$.message").value("No applicable price found for product 35455 and brand 1"))
                .andExpect(jsonPath("$.status").value(404));
    }
}