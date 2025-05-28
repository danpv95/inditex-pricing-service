DROP TABLE IF EXISTS prices;

CREATE TABLE prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    price_list INT,
    product_id BIGINT,
    priority INT,
    price DECIMAL(10,2),
    curr VARCHAR(3)
);

CREATE INDEX idx_price_lookup
    ON prices (brand_id, product_id, start_date, end_date);
