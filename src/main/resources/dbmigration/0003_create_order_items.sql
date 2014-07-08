create table order_items(
  id serial primary key,
  productId integer references products,
  quantity integer ,
  orderId integer references orders
);
