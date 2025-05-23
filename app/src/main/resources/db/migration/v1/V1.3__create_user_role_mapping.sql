create table user_role_mapping
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint fk_user_role_mapping__user foreign key (user_id) references "user" (id),
    constraint fk_user_role_mapping__role foreign key (role_id) references role (id)
);