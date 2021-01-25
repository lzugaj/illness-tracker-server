-- insert data into tables
insert into "ROLE" values (1, 'PATIENT', 'The user role can perform CRUD operations over his/her profile and illnesses.');
insert into "ROLE" values (2, 'DOCTOR', 'The doctor role can perform specific CRUD operations over application users and illnesses.');
insert into "ROLE" values (3, 'ADMIN', 'The admin role can perform CRUD operations over all application users and illnesses.');

insert into "ILLNESS" values (1, 'Body Mass Index', false);
insert into "ILLNESS" values (2, 'Hypertension', false);
insert into "ILLNESS" values (3, 'Hyperthyroidism', false);
insert into "ILLNESS" values (4, 'Diabetes Mellitus Type II', false);
insert into "ILLNESS" values (5, 'Painful Syndromes', false);
insert into "ILLNESS" values (6, 'Gastro Esophageal Reflux', false);

insert into "BODY_MASS_INDEX_INFO" values (1, 'Below 18.5', 'Underweight');
insert into "BODY_MASS_INDEX_INFO" values (2, '18.5-24.9', 'Normal weight');
insert into "BODY_MASS_INDEX_INFO" values (3, '25.0-29.9', 'Overweight');
insert into "BODY_MASS_INDEX_INFO" values (4, '30.0-34.9', 'Obesity class I');
insert into "BODY_MASS_INDEX_INFO" values (5, '35.0-39.9', 'Obesity class II');
insert into "BODY_MASS_INDEX_INFO" values (6, 'Above 40', 'Obesity class III');

insert into "HYPERTENSION_INFO" values (1, 'Less than 120', 'Less than 80', 'Normal');
insert into "HYPERTENSION_INFO" values (2, '120-129', 'Less than 80', 'Elevated');
insert into "HYPERTENSION_INFO" values (3, '130-139', '80-89', 'High Blood Pressure Stage 1');
insert into "HYPERTENSION_INFO" values (4, '140-180', '90-120', 'High Blood Pressure Stage 2');
insert into "HYPERTENSION_INFO" values (5, 'Higher than 180', 'Higher than 120', 'Hypertensive Crisis');

insert into "HYPERTHYROIDISM_INFO" values (1, '0.2-4.3 mlU/ml', '0.8-2.1 ng/ml', '5.0-14.1 μg/ml', 'Reference range');
insert into "HYPERTHYROIDISM_INFO" values (2, '2.23±0.93 mlU/ml', '1.052±0.17 ng/ml', '7.42±1.63 μg/ml', 'Euthyroid');
insert into "HYPERTHYROIDISM_INFO" values (3, '17.37±10.54 mlU/ml', '1.062±0.28 ng/ml', '7.52±1.53 μg/ml', 'Subclinical hypothyroid');
insert into "HYPERTHYROIDISM_INFO" values (4, '18.67±11.34 mlU/ml', '0.82±0.41 ng/ml', '5.54±1.53 μg/ml', 'Primary range');