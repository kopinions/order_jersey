create table products(
  id serial primary key,
  name varchar,
  description varchar
);

create table prices(
  id serial primary key,
  effectDate date,
  amount numeric ,
  productId int references products
);

