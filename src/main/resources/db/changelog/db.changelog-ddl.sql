-- drop tables
drop table if exists "ROLE";
drop table if exists "USER";
drop table if exists "USER_ROLE";
drop table if exists "ILLNESS";
drop table if exists "BODY_MASS_INDEX_INFO";
drop table if exists "BODY_MASS_INDEX";
drop table if exists "USER_BODY_MASS_INDEX";
drop table if exists "HYPERTENSION_INFO";
drop table if exists "HYPERTENSION";
drop table if exists "USER_HYPERTENSION";
drop table if exists "HYPERTHYROIDISM";
drop table if exists "USER_HYPERTHYROIDISM";
drop table if exists "DUMMY";

-- create tables
create table "ROLE" (
    id bigserial not null,
    name varchar(20),
    description varchar(100),
    primary key (id)
);

create table "USER" (
    id bigserial not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(50) not null,
    username varchar(50) not null,
    password varchar(50) not null,
    date_of_birth date not null,
    phone_number varchar(14) not null,
    gender varchar(10) not null,
    registration_date timestamp,
    status varchar(10),
    is_body_mass_index_active boolean,
    is_hypertension_active boolean,
    is_hyperthyroidism_active boolean,
    is_diabetes_mellitus_type_II_active boolean,
    is_painful_syndromes_active boolean,
    is_gastro_esophageal_reflux_active boolean,
    primary key (id)
);

create table "USER_ROLE" (
    id bigserial not null,
    user_id int not null,
    role_id int not null,
    primary key (id),
    constraint fk_user_role foreign key (user_id) references "USER" (id),
    constraint fk_role_user foreign key (role_id) references "ROLE" (id)
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

create table "USER_BODY_MASS_INDEX" (
    user_id int not null,
    bmi_id int not null,
    primary key (user_id, bmi_id),
    constraint fk_user_bmi foreign key (user_id) references "USER" (id),
    constraint fk_bmi_user foreign key (bmi_id) references "BODY_MASS_INDEX" (id)
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

create table "USER_HYPERTENSION" (
    user_id int not null,
    hypertension_id int not null,
    primary key (user_id, hypertension_id),
    constraint fk_user_hypertension foreign key (user_id) references "USER" (id),
    constraint fk_hypertension_user foreign key (hypertension_id) references "HYPERTENSION" (id)
);

create table "HYPERTHYROIDISM_INFO" (
    id bigserial not null,
    tsh_range varchar(50),
    ft3_range varchar(50),
    ft4_range varchar(50),
    classification varchar(100),
    primary key (id)
);

create table "HYPERTHYROIDISM" (
    id bigserial not null,
    tsh decimal(5, 2) not null,
    ft3 decimal(5, 2) not null,
    ft4 decimal(5, 2) not null,
    date_of_performed_measurement timestamp,
    primary key (id)
);

create table "USER_HYPERTHYROIDISM" (
    user_id int not null,
    hyperthyroidism_id int not null,
    primary key (user_id, hyperthyroidism_id),
    constraint fk_user_hyperthyroidism foreign key (user_id) references "USER" (id),
    constraint fk_hyperthyroidism_user foreign key (hyperthyroidism_id) references "HYPERTHYROIDISM" (id)
);

create table "DIABETES_MELLITUS_TYPE_II" (
    id bigserial not null,
    guk0 decimal(5, 2) not null,
    guk2 decimal(5, 2) not null,
    description varchar(512),
    date_of_performed_measurement timestamp,
    primary key (id)
);

create table "USER_DIABETES_MELLITUS_TYPE_II" (
    user_id int not null,
    dmt_id int not null,
    primary key (user_id, dmt_id),
    constraint fk_user_dmt foreign key (user_id) references "USER" (id),
    constraint fk_dmt_user foreign key (dmt_id) references "DIABETES_MELLITUS_TYPE_II" (id)
);

create table "PAINFUL_SYNDROME" (
    id bigserial not null,
    body_part varchar(100) not null,
    description varchar(1024)not null,
    vas_value int not null,
    date_of_performed_measurement timestamp,
    primary key (id)
);

create table "USER_PAINFUL_SYNDROME" (
    user_id int not null,
    ps_id int not null,
    primary key (user_id, ps_id),
    constraint fk_user_ps foreign key (user_id) references "USER" (id),
    constraint fk_ps_user foreign key (ps_id) references "PAINFUL_SYNDROME" (id)
);

create table "GASTRO_ESOPHAGEAL_REFLUX" (
    id bigserial not null,
    datetime_of_last_meal timestamp not null,
    datetime_of_onset_of_symptoms timestamp not null,
    description varchar(512),
    date_of_performed_measurement timestamp,
    primary key (id)
);

create table "USER_GASTRO_ESOPHAGEAL_REFLUX" (
    user_id int not null,
    ger_id int not null,
    primary key (user_id, ger_id),
    constraint fk_user_ger foreign key (user_id) references "USER" (id),
    constraint fk_ger_user foreign key (ger_id) references "GASTRO_ESOPHAGEAL_REFLUX" (id)
);

create sequence dummy_id_sequence start with 1 increment by 1 minvalue 1 maxvalue 99999;
create table "DUMMY" (
    id bigserial not null,
    primary key (id)
);