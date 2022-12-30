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
<<<<<<< HEAD
    id   bigserial not null primary key,
=======
    client_id   bigserial not null primary key,
>>>>>>> origin/lesson24_wc
    name varchar(50),
    address_id bigserial
);