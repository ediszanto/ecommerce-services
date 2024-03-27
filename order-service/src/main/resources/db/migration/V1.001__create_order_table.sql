CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    date TIMESTAMP NOT NULL,
    total_cost DOUBLE PRECISION NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    payment_type VARCHAR(5) NOT NULL,
    payment_status VARCHAR(10),
    status VARCHAR(10) NOT NULL
);
