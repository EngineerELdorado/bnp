create table authors
(
    id   bigserial    not null,
    name varchar(255) not null,
    primary key (id)
);
create table books
(
    id               bigserial    not null,
    name             varchar(255) not null,
    price            float(53)    not null,
    publication_year integer      not null,
    author_id        bigint,
    primary key (id)
);
alter table if exists books
    add constraint FKfjixh2vym2cvfj3ufxj91jem7 foreign key (author_id) references authors;