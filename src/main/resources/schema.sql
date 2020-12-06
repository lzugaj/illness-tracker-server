-- drop tables
drop table if exists "ROLE";
drop table if exists "PATIENT";
drop table if exists "PATIENT_ROLE";

-- create tables
create table "ROLE" (
    id bigserial not null,
    name varchar(10) not null,
    primary key (id)
);

create table "PATIENT" (
    id bigserial not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(50) not null,
    password varchar(50) not null,
    oib varchar(11) not null,
    date_of_birth date not null,
    phone_number varchar(14) not null,
    gender varchar(10),
    date_of_registration timestamp,
    is_active boolean,
    primary key (id)
);

create table "PATIENT_ROLE" (
    patient_id int not null,
    role_id int not null,
    primary key (patient_id, role_id),
    constraint fk_patient foreign key (patient_id) references "PATIENT" (id),
    constraint fk_role foreign key (role_id) references "ROLE" (id)
);