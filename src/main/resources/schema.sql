-- drop tables
drop table if exists "ROLE";
drop table if exists "PATIENT";
drop table if exists "PATIENT_ROLE";
drop table if exists "BODY_MASS_INDEX";
drop table if exists "PATIENT_BODY_MASS_INDEX";

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
    id bigserial not null,
    patient_id int not null,
    role_id int not null,
    primary key (id),
    constraint fk_patient_role foreign key (patient_id) references "PATIENT" (id),
    constraint fk_role foreign key (role_id) references "ROLE" (id)
);

create table "BODY_MASS_INDEX_INFO" (
    id bigserial not null,
    value varchar(50) not null,
    classification varchar(100) not null,
    primary key (id)
);

create table "BODY_MASS_INDEX" (
    id bigserial not null,
    height decimal(5, 1) not null,
    weight decimal(5, 1) not null,
    index_value decimal(5, 1),
    date_of_performed_measurement timestamp,
    is_active boolean,
    bmi_info_id bigint not null,
    primary key (id),
    constraint fk_bmi_info foreign key (bmi_info_id) references "BODY_MASS_INDEX_INFO" (id)
);

create table "PATIENT_BODY_MASS_INDEX" (
    patient_id int not null,
    bmi_id int not null,
    primary key (patient_id, bmi_id),
    constraint fk_patient_bmi foreign key (patient_id) references "PATIENT" (id),
    constraint fk_bmi foreign key (bmi_id) references "BODY_MASS_INDEX" (id)
);