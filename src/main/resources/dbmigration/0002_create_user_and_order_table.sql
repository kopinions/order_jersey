create table users(
  id serial primary key,
  name varchar
);

create table orders(
  id serial primary key,
  address varchar ,
  name varchar ,
  phone varchar,
  userId integer references users
);
