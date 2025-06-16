create table carts_with_products_read_model_entity 
(
  cart_id uuid not null,
  product_id uuid not null,
  primary key (cart_id, product_id)
);
