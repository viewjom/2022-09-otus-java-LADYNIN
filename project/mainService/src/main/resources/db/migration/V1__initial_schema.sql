create table clients
(
    id   bigserial not null primary key,
    name varchar(50)
);

create table addresses
(
    id      bigserial not null primary key,
    zip     varchar(20),
    street  varchar(100),
    house   varchar(100),
    guid   varchar(100),
    client_id bigserial references clients (id)
);

create table phones
(
    id      bigserial not null primary key,
    number  varchar(20),
    client_id bigserial references clients (id)
);

create table documents
(
    id        bigserial not null primary key,
    file_name varchar(100),
    file      bytea,
    hash512      varchar(32000),
    hash256      varchar(32000),
    client_id bigserial references clients (id)
);