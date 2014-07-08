create table payments(
  id serial primary key,
  payType varchar ,
  amount numeric,
  orderId integer references orders
);
