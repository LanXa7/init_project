create table "user"
(
    "id"        bigserial primary key,
    username    varchar(50)  not null,
    "password"  varchar(256) not null,
    email       varchar(50)  not null,
    avatar      varchar(255),
    modified_by bigint,
    created_at  TIMESTAMP    not null default CURRENT_TIMESTAMP,
    modified_at TIMESTAMP,
    constraint uk_user_unique unique (username, email)
);