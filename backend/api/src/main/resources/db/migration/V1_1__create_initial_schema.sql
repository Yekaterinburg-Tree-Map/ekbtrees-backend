-- Инициализация postgis

create extension if not exists postgis;
create extension if not exists postgis_topology;


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
