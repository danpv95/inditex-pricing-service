package com.bcnc.inditex_pricing_service.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Price {

    private final Long id;
    private final Long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priceList;
    private final Long productId;
    private final Integer priority;
    private final BigDecimal price;
    private final String currency;

    private Price(Long id, Long brandId, LocalDateTime startDate, LocalDateTime endDate,
                  Integer priceList, Long productId, Integer priority,
                  BigDecimal price, String currency) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        this.id = id;
        this.brandId = Objects.requireNonNull(brandId);
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);
        this.priceList = Objects.requireNonNull(priceList);
        this.productId = Objects.requireNonNull(productId);
        this.priority = Objects.requireNonNull(priority);
        this.price = Objects.requireNonNull(price);
        this.currency = Objects.requireNonNull(currency);
    }

    public static Price of(Long id, Long brandId, LocalDateTime startDate, LocalDateTime endDate,
                           Integer priceList, Long productId, Integer priority,
                           BigDecimal price, String currency) {
        return new Price(id, brandId, startDate, endDate, priceList, productId, priority, price, currency);
    }

    public static Price withoutId(Long brandId, LocalDateTime startDate, LocalDateTime endDate,
                                  Integer priceList, Long productId, Integer priority,
                                  BigDecimal price, String currency) {
        return new Price(null, brandId, startDate, endDate, priceList, productId, priority, price, currency);
    }

    public Long getId() { return id; }
    public Long getBrandId() { return brandId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Integer getPriceList() { return priceList; }
    public Long getProductId() { return productId; }
    public Integer getPriority() { return priority; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
}
