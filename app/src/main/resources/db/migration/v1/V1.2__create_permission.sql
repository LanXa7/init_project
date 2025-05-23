CREATE TABLE permission
(
    "id"        bigserial primary key, ,
    code        varchar(50) not null,
    created_by  bigint      not null default 1,
    modified_by bigint      not null default 1,
    created_at  TIMESTAMP   not null DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP   not null DEFAULT CURRENT_TIMESTAMP,
    constraint uk_permission_unique unique (code)
);