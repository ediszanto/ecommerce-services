CREATE TABLE IF NOT EXISTS product (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(50) NOT NULL,
    quantity numeric,
    price double precision,
    category_id integer,
    CONSTRAINT fk_category_id
      FOREIGN KEY(category_id)
        REFERENCES product_category(id)
);