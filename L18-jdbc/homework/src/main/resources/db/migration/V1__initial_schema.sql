create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

create table manager
(
    no   bigserial not null primary key,
    label varchar(50),
    param1 VARCHAR(50)
);