CREATE TABLE IF NOT EXISTS order_item (
    id SERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES product_schema.product(id),
    quantity INTEGER NOT NULL,
    unit_price DOUBLE PRECISION NOT NULL,
    order_id INTEGER,
--    REFERENCES order_schema.orders(id)

    CONSTRAINT fk_order
        FOREIGN KEY (order_id)
            REFERENCES order_schema.orders(id)
);
