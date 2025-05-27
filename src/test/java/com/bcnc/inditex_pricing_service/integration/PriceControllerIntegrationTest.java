package com.bcnc.inditex_pricing_service.integration;

import com.bcnc.InditexPricingServiceApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InditexPricingServiceApplication.class)
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/v1/prices";

    @Test
    @DisplayName("Test 1: 14-Jun 10:00 - priceList 1")
    void testCase1() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    @DisplayName("Test 2: 14-Jun 16:00 - priceList 2")
    void testCase2() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2));
    }

    @Test
    @DisplayName("Test 3: 14-Jun 21:00 - priceList 1")
    void testCase3() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    @DisplayName("Test 4: 15-Jun 10:00 - priceList 3")
    void testCase4() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-15T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3));
    }

    @Test
    @DisplayName("Test 5: 15-Jun 12:00 - priceList 1")
    void testCase5() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-15T12:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    @DisplayName("Test 6: 15-Jun 16:30 - priceList 4")
    void testCase6() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-15T16:30:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4));
    }

    @Test
    @DisplayName("Test 7: 16-Jun 21:00 - priceList 4")
    void testCase7() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-16T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4));
    }

    @Test
    @DisplayName("Test 8: 31-Dec 23:59 - priceList 4")
    void testCase8() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4));
    }

    @Test
    @DisplayName("Test 9: 14-Jun 14:59 - priceList 1")
    void testCase9() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T14:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    @DisplayName("Test 10: 14-Jun 18:30:01 - priceList 1")
    void testCase10() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T18:30:01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    @DisplayName("Test 11: 13-Jun 23:59 - 404 Not Found")
    void testCase11() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-13T23:59:59"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test 12: 01-Jan-2021 - 404 Not Found")
    void testCase12() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2021-01-01T00:00:00"))
                .andExpect(status().isNotFound());
    }
}
