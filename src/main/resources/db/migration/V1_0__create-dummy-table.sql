create table dummy
(
    id bigserial,
    code varchar(5) not null,
    name varchar(50) not null,
    category varchar(50),
    description varchar(250),
    created_at timestamp not null,
    updated_at timestamp
);
