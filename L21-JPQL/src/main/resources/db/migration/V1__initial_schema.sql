
create table addresses
(
    id      bigserial not null primary key,
    street  varchar(100)
);

create table phones
(
    id      bigserial not null primary key,
    number  varchar(20),
    client_id bigserial
);

create table clients
(
    id   bigserial not null primary key,
    name varchar(50),
    address_id bigserial
);