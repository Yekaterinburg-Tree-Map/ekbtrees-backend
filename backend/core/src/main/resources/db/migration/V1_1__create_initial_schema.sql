-- Инициализация postgis

create extension if not exists postgis;
create extension if not exists postgis_topology;

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


-- Породы деревьев
create sequence species_tree_id_seq start with 1;

create table species_tree
(
    id                     bigint    not null
        constraint species_tree_pkey
            primary key,
    creation_date          timestamp not null,
    last_modification_date timestamp not null,
    title                  varchar(255)
);


-- Деревья
create sequence tree_id_seq start with 1;

create table trees
(
    id                         bigint not null
        constraint trees_pkey
            primary key,
    creation_date              timestamp not null,
    last_modification_date     timestamp not null,
    age                        integer,
    condition_assessment       integer,
    diameter_of_crown          double precision,
    geo_point                  geometry not null,
    height_of_the_first_branch double precision,
    number_of_tree_trunks      integer,
    status                     varchar(255),
    tree_height                double precision,
    tree_planting_type         varchar(255),
    trunk_girth                double precision,
    author_id                  bigint not null
        constraint trees_author_id
            references users,
    species_id                 bigint
        constraint trees_species_id
            references species_tree
);


-- Комментарии
create sequence comment_id_seq start with 1;

create table comments
(
    id                     bigint    not null
        constraint comments_pkey
            primary key,
    creation_date          timestamp not null,
    last_modification_date timestamp not null,
    text                   varchar(255),
    user_id                bigint    not null,
    tree_id                bigint    not null
        constraint comments_tree_id
            references trees
);


-- Файлы
create sequence file_entity_id_seq start with 1;

create table files
(
    id                     bigint    not null
        constraint files_pkey
            primary key,
    creation_date          timestamp not null,
    last_modification_date timestamp not null,
    hash                   varchar(255),
    mime_type              varchar(255),
    size                   bigint,
    title                  varchar(255),
    uri                    varchar(255),
    tree_id                bigint
        constraint files_tree_id
            references trees,
    author_id              bigint
        constraint files_user_id
            references users
);

create sequence example_id_seq start with 1;

create table example
(
    id                     bigint    not null
        constraint example_pkey
            primary key,
    creation_date          timestamp not null,
    last_modification_date timestamp not null,
    some_value             varchar(255) not null
);
