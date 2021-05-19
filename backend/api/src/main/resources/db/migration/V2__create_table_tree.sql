CREATE SEQUENCE IF NOT EXISTS tree_id_seq START WITH 1;

CREATE TABLE IF NOT EXISTS tree (
    id bigint PRIMARY KEY DEFAULT nextval('tree_id_seq'),
    geographical_point geometry NOT NULL,
    species_id bigint,
    tree_height double precision,
    number_tree_trunks int,
    trunk_girth double precision,
    diameter_crown double precision,
    height_first_branch double precision,
    condition_assessment int,
    age int,
    tree_planting_type text,
    created timestamp with time zone NOT NULL DEFAULT now(),
    updated timestamp with time zone,
    author_id bigint,
    status text
);