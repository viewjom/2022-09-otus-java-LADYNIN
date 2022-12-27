
create table addresses
(
    address_id      bigserial not null primary key,
    street  varchar(100)
);

create table phones
(
    id      bigserial not null primary key,
    number  varchar(20)
);

create table clients
(
    client_id   bigserial not null primary key,
    name varchar(50)
);