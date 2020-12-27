-- drop tables
drop table if exists "ROLE";
drop table if exists "PATIENT";
drop table if exists "PATIENT_ROLE";
drop table if exists "ILLNESS";
drop table if exists "BODY_MASS_INDEX_INFO";
drop table if exists "BODY_MASS_INDEX";
drop table if exists "PATIENT_BODY_MASS_INDEX";
drop table if exists "HYPERTENSION_INFO";
drop table if exists "HYPERTENSION";
drop table if exists "PATIENT_HYPERTENSION";
drop table if exists "DUMMY";

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
    is_body_mass_index_active boolean,
    is_hypertension_active boolean,
    is_hyperthyroidism_active boolean,
    is_diabetes_mellitus_type_II_active boolean,
    is_painful_syndromes_active boolean,
    is_gastro_esophageal_reflux_active boolean,
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

create table "ILLNESS" (
    id bigserial not null,
    name varchar(100),
    is_selected boolean,
    primary key (id)
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

create table "HYPERTENSION_INFO" (
    id bigserial not null,
    systolic_range varchar(50) not null,
    diastolic_range varchar(50) not null,
    classification varchar(100) not null,
    primary key (id)
);

create table "HYPERTENSION" (
   id bigserial not null,
   systolic int not null,
   diastolic int not null,
   date_of_performed_measurement timestamp,
   hypertension_info_id bigint not null,
   primary key (id),
   constraint fk_hypertension_info foreign key (hypertension_info_id) references "HYPERTENSION_INFO" (id)
);

create table "PATIENT_HYPERTENSION" (
   patient_id int not null,
   hypertension_id int not null,
   primary key (patient_id, hypertension_id),
   constraint fk_patient_hypertension foreign key (patient_id) references "PATIENT" (id),
   constraint fk_hypertension foreign key (hypertension_id) references "HYPERTENSION" (id)
);

create sequence dummy_id_sequence start with 1 increment by 1 minvalue 1 maxvalue 99999;
create table "DUMMY" (
    id bigserial not null,
    primary key (id)
);