create table role_permission_mapping
(
    role_id       bigint not null,
    permission_id bigint not null,
    primary key (role_id, permission_id),
    constraint fk_role_permission_mapping__role foreign key (role_id) references role (id),
    constraint fk_role_permission_mapping__permission foreign key (permission_id) references permission (id)
);