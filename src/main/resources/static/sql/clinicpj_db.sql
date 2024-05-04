/*DROP DATABASE medicalexam_pj_db;
SET foreign_key_checks = 0;*/

DROP DATABASE clinicpj_db;
SET foreign_key_checks = 0;
CREATE DATABASE clinicpj_db;
USE clinicpj_db;

CREATE TABLE roles
(
	role_id int(11) not null auto_increment,
    role_name varchar(255),
    primary key (role_id)
);

INSERT INTO roles
VALUES(1,"DOCTOR"), (2,"PATIENT"), (3,"ADMIN");

CREATE TABLE statuses
(
	status_id int(11) not null auto_increment,
    status_name varchar(255),
    primary key (status_id)
);

INSERT INTO statuses
VALUES(1,"Waiting"), (2,"Processing"), (3,"Ending");

CREATE TABLE specializations
(
	specialization_id int(11) not null auto_increment,
    specialization_name varchar(255),
    specialization_description text,
    created_at datetime,
	updated_at datetime,
    deleted_at datetime,
    primary key (specialization_id)
);

INSERT INTO specializations(specialization_name,specialization_description)
VALUES ('Cardiology','Positive'),
('Ophthalmology','More Positive'),
('Dermatology','More And More Positive'),
('Respiratory','Positive'),
('Neurosurgery','More Positive'),
('Gastroenterology','More And More Positive'),
('Endoscopy','Positive');

CREATE TABLE clinics
(
	clinic_id int(11) not null auto_increment,
    clinic_name varchar(255),
    clinic_address varchar(255),
    user_phone_number varchar(255),
    clinic_description text,
    clinic_cost varchar(255),
    created_at datetime,
    primary key (clinic_id)
);

INSERT INTO clinics(clinic_name,clinic_address,clinic_description,clinic_cost)
VALUES ("Clinic-BV001","001001","BV001 is the best","200k"),
("Clinic-BV002","002002","BV002 is the best","2000k"),
("Clinic-BV001","001002","BV001 is the best","200k"),
("Clinic-BV003","003001","BV003 is the best","300k"),
("Clinic-BV004","004001","BV004 is the best","400k"),
("Clinic-BV005","005001","BV005 is the best","2000k"),
("Clinic-BV006","006001","BV006 is the best","2000k"),
("Clinic-BV002","002002","BV002 is the best","300k"),
("Clinic-BV002","002003","BV002 is the best","200k"),
("Clinic-BV001","001003","BV001 is the best","200k"),
("Clinic-BV001","001004","BV001 is the best","300k"),
("Clinic-BV003","003002","BV003 is the best","200k"),
("Clinic-BV003","003003","BV003 is the best","200k"),
("Clinic-BV004","004002","BV004 is the best","300k"),
("Clinic-BV005","005005","BV005 is the best","400k"),
("Clinic-BV001","001005001","BV001 is the best","200k"),
("Clinic-BV001","001006001","BV001 is the best","300k"),
("Clinic-BV002","002004001","BV002 is the best","2000k");

CREATE TABLE users
(
	user_id int(11) not null auto_increment,
    user_name varchar(255),
    user_email varchar(255),
    user_password varchar(255),
    user_birth_day varchar(255),
    user_gender varchar(255),
    user_address varchar(255),
    user_phone_number varchar(255),
    user_description text,
    role_id int(11),
    is_active tinyint(1),
	created_at datetime,
	updated_at datetime,
    deleted_at datetime,
    primary key (user_id),
    constraint foreign key (role_id) references roles (role_id)
);

INSERT INTO users(user_name,user_email,user_password,user_birth_day,user_gender,user_address,user_phone_number,user_description,role_id, is_active)
VALUES ("Tran Van Minh","tranvanminh@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19900102","Male","123 tvm","090001001","Nice",1,1),
("Ngo Thi Nam","ngothinam@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19910210","Female","234 ntn","090002002","Nice",1,1),
("Truong Quoc Hung","truongquochung@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19920315","Male","678 tqh","090003003","Nice",1,1),
("Le Van Thanh","levanthanh@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19941022","Female","098 lvt","090004004","Nice",2,1),
("Ha Anh Hieu","haanhhieu@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19951230","Male","453 hah","090005005","Nice",2,1),
("Nguyen Minh Anh","nguyenminhanh@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19960918","Female","022 nma","090006006","Nice",2,1),
("Ngo Quynh Nhu","ngoquynhnhu@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19930415","Female","654 nqn","090007007","Nice",2,1),
("Tran Minh Tuan","tranminhtuan@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19960918","Male","4454 tmt","090008008","Nice",2,1),
("Nguyen Nam Thu","nguyennamthu@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19960918","Female","3534 nnt","090009009","Nice",2,1),
("Cao Minh Dat","caominhdat@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19960918","Male","1454 cmd","090010010","Nice",2,1),
("Tran Phi Hung","tranphihung@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19901007","Male","955 tph","090011011","Nice",2,1),
("Luc Ngoc Thu","lucngocthu@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19920308","Female","2850 lnt","090129012","Nice",2,1),
("Ho Minh Nhan","hominhnhan@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19940829","Female","945 hmn","090013013","Nice",2,1),
("Lam Nhat Hoang","lamnhathoang@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19950519","Male","7329 lnh","09014014","Nice",2,1),
("Bui Quang Dang","buiquangdang@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19891121","Male","234 bqd","090015015","Nice",2,1),
("Tran Tuyet Ngoc","trantuyetngoc@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19920812","Female","8397 ttn","090016016","Pretty",3,1),
("Bui Quoc Hieu","buiquochieu@email.com","$2a$10$ux.erEbY1V0PFr6RESOzTO02o3HIZRLFRycWNuQ9/2vBB5d3BMDp2","19930417","Male","269 bqh","090017017","Good",3,1);

CREATE TABLE schedules
(
	schedule_id int(11) not null auto_increment,
    schedule_date varchar(255),
    schedule_time varchar(255),
    doctor_id int(11),
    is_active tinyint(1),
    created_at datetime,
	updated_at datetime,
    deleted_at datetime,
    primary key (schedule_id),
    constraint foreign key (doctor_id) 
    references users (user_id)
);

INSERT INTO schedules(schedule_date,schedule_time,doctor_id,is_active)
VALUES ("20240103","18h","1",1),
("20240218","08h","1",1),
("20240128","10h30","1",1),
("20240228","11h","1",1),
("20240331","09h15","1",1),
("20240218","13h30","1",1),
("20240128","14h","1",1),
("20240228","14h","1",1),
("202404","13h","1",1),
("20240220","10h30","2",1),
("20240104","18h","2",1),
("20240311","17h","2",1),
("20240117","15h","2",1),
("20240218","15h30","2",1),
("20240326","15h","2",1),
("20240408","16h","2",1),
("20240208","16h30","2",1);


CREATE TABLE patients
(
	patient_id int(11) not null auto_increment,
    user_id int(11),
    status_id int(11),
    doctor_id int(11),
    primary key (patient_id),
    constraint foreign key (user_id) references users (user_id),
	constraint foreign key (status_id) references statuses (status_id),
    constraint foreign key (doctor_id) references users (user_id)
);
INSERT INTO patients(user_id,status_id,doctor_id)
VALUES (8,1,1),(8,2,1),(9,1,1),(9,2,1),(9,3,1),(8,1,2),(8,2,2),(8,3,2),(9,1,2),(9,2,2),(10,2,3),(11,1,3),(11,3,3);

CREATE TABLE extra_info
(
	extra_info_id int(11) not null auto_increment,
    history_breath text,
    more_info text,
    patient_id int (1),
    created_at datetime,
	updated_at datetime,
    deleted_at datetime,
    primary key (extra_info_id),
    constraint foreign key (patient_id) references patients (patient_id)
);

INSERT INTO extra_info(history_breath,more_info,patient_id,created_at)
VALUES (1,"Not Bad",1,"20240102"),
(1,"Quite Good",2,"20240105"),
(0,"Little Bad",3,"20240115"),
(1,"Really Good",4,"20240119"),
(0,"Quite Bad",5,"20240204"),
(1,"Good",6,"20240206"),
(1,"As Usual",7,"20240211"),
(0,"Awfully Bad",8,"20240226"),
(1,"Quite Good, little Bad",9,"20240313"),
(0,"Really Bad, little Good",10,"20240402");

CREATE TABLE doctor_patients
(
	doctor_patient_id int(11) not null auto_increment,
    doctor_id int(11),
    clinic_id int(11),
    specialization_id int(11),
    primary key (doctor_patient_id),
    constraint foreign key (doctor_id) references users (user_id),
	constraint foreign key (clinic_id) references clinics (clinic_id),
    constraint foreign key (specialization_id) references specializations (specialization_id)

);
INSERT INTO doctor_patients(doctor_id,clinic_id,specialization_id)
VALUES (1,1,1),(1,2,2),(1,3,3),(2,4,4),(2,5,5),(2,6,6),(3,7,7);

CREATE TABLE user_token
(
	id int(11) not null auto_increment,
    token varchar(255),
    expiry_date datetime,
    user_id int(11),
    primary key (id),
    constraint foreign key (user_id) references users (user_id)
);

CREATE TABLE user_schedules
(
	user_schedule_id int(11) not null auto_increment,
    user_id int(11),
    schedule_id int(11),
    primary key (user_schedule_id),
    constraint foreign key (user_id) references users (user_id),
	constraint foreign key (schedule_id) references schedules (schedule_id)
);

INSERT INTO user_schedules(user_id,schedule_id)
VALUES (8,1),(9,2),(10,3),(11,4),(12,5),(13,6),(14,7),(15,8),(8,9),(9,10),(10,11),(11,12),(12,13),(13,14),(14,15),(15,16),(8,17),
(2,3),(2,1),(2,2),(3,8),(3,9),(3,13),(1,5),(1,7),(1,1),(1,2),(1,2);

CREATE TABLE doctor_extra_info
(
	doctor_extra_info_id int(11) not null auto_increment,
    general_intro varchar(255),
    training_processing varchar(255),
    achievement varchar(255),
    department varchar(255),
    doctor_id int(11),
    primary key (doctor_extra_info_id),
    constraint foreign key (doctor_id ) references users (user_id)
);

INSERT INTO doctor_extra_info(general_intro,training_processing,achievement,department,doctor_id)
VALUES ("Best Doctor Ever", "Most Exellent Training Ever", "25 years of exp", "Varirous Departments",1),
("Happiest Doctor Ever", "Greatest Training Ever", "13 years of exp", "a Few Exceptional Departments",2),
("Most Decent Doctor Ever", "Most Brilliant Training Ever", "18 years of exp", "A Huge Number of Departments",3);