-- Пользователи
create sequence user_id_seq start with 1;

create table users
(
    id                     bigint    not null
        constraint users_pkey
            primary key,
    creation_date          timestamp not null,
    last_modification_date timestamp not null,
    email                  varchar(255) unique,
    enabled                boolean   not null,
    fb_id                  bigint,
    first_name             varchar(255),
    last_name              varchar(255),
    password               varchar(255),
    phone                  varchar(255),
    provider               varchar(255),
    vk_id                  bigint
);


-- Роли
create sequence role_id_seq start with 1;

create table roles
(
    id                     bigint       not null
        constraint roles_pkey
            primary key,
    name                   varchar(255) not null unique,
    creation_date          timestamp    not null,
    last_modification_date timestamp    not null
);

create table users_roles
(
    user_id bigint not null
        constraint users_roles_user_id references users,
    role_id bigint not null
        constraint users_roles_role_id references roles,
    constraint users_roles_pkey
        primary key (role_id, user_id)
);


-- Токены
create sequence refresh_token_id_seq start with 1;

create table refresh_tokens
(
    id                     bigint    not null
        constraint refresh_tokens_pkey
            primary key,
    creation_date          timestamp not null,
    last_modification_date timestamp not null,
    token                  text      not null,
    user_id                bigint    not null
        constraint refresh_tokens_user_id references users
);
