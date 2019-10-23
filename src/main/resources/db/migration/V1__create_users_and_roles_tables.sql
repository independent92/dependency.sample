create table role (
   id bigserial primary key,
   title text not null
);

create table account (
   id bigserial primary key,
   first_name text not null,
   middle_name text,
   last_name text not null,
   email text not null unique,
   password text not null,
   enabled boolean not null
);

create table account_role (
    account_id bigint not null,
    role_id bigint not null,
    primary key (account_id, role_id),
    foreign key(account_id) references account(id),
    foreign key(role_id) references role(id)
);

insert into role(title) values ('admin');
insert into role(title) values ('user');