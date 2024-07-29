create database jobSeeking;
use jobSeeking;

create table roles (
	role_id int auto_increment primary key,
    role_name varchar(50)
);
insert into roles (role_name) values ('ADMIN'), ('EMPLOYEE'), ('EMPLOYER');

create table users(
	user_id varchar(100),
    email varchar(200),
    password varchar(200),
    phone varchar(50),
    role_id int,
    primary key (user_id, role_id)
);
alter table users add constraint fk_user_role_id foreign key (role_id) references roles(role_id);

create table admins (
	user_id varchar(100) primary key,
    fullname varchar(100)
);
alter table admins add constraint fk_admin_user_id foreign key (user_id) references users(user_id);

create table candidates (
	user_id varchar(100),
    fullname varchar(100),
    dob timestamp,
    position varchar(255),
    current_salary varchar(255),
    self_desc text,
    gender enum('male', 'female'),
    age varchar(100),
    salary_expect varchar(255),
    facebook varchar(255),
    linkedin varchar(255),
    pro_id int,

	primary key (user_id)
);
alter table candidates add constraint fk_candidate_user_id foreign key (user_id) references users(user_id);
alter table candidates add constraint fk_candidate_pro_id foreign key (pro_id) references provinces(pro_id);

create table avatar(
	user_id varchar(100) primary key,
    name varchar(100),
    type varchar(100),
    data longblob
);
alter table avatar add constraint fk_avatar_user_id foreign key (user_id) references users(user_id);

create table employers (
	user_id varchar(100),
    background longblob,
	emp_name varchar(100),
    website varchar(255),
    about_us text,
    address varchar(255),
    facebook varchar(255),
    linkedin varchar(255),
    location varchar(255),
    pro_id int,
    
    primary key (user_id)
);
alter table employers add constraint fk_employer_user_id foreign key (user_id) references users(user_id);
alter table employers add constraint fk_employer_pro_id foreign key (pro_id) references provinces(pro_id);

create table post(
	post_id int auto_increment unique,
	user_id varchar(100),
	job_title varchar(255) not null,
    job_desc text,
	status_id int,
	min_salary bigint,
	max_salary bigint,
	end_date date,
    tech text,

	primary key(user_id, post_id)
);
alter table post add constraint fk_user_post_id foreign key (user_id) references employers (user_id);
alter table post add constraint fk_post_status_id foreign key (status_id) references post_status (status_id);

create table post_status (
	status_id int auto_increment primary key,
    status_title varchar(50)
);
insert into post_status (status_title) values ('Pending'), ('Active'), ('Fullfilled'), ('Expired'), ('Rejected');

create table requirement_desc (
	req_id int auto_increment,
	post_id int,
    req_title varchar(255),
    req_desc text,
    primary key(req_id, post_id)
);
alter table requirement_desc add constraint fk_req_post_id foreign key (post_id) references post(post_id);

create table job_level (
	level_id int auto_increment primary key,
    level_title varchar(100)
);
insert into job_level(level_title) values('All levels'),('Intern'),('Fresher'),('Junior'),('Middle'),('Senior'),('Leader'),('Manager');

create table post_job_level (
	post_id int,
    level_id int,
    primary key (post_id, level_id)
);
alter table post_job_level add constraint fk_level_post_id foreign key (level_id) references job_level(level_id);
alter table post_job_level add constraint fk_post_post_id foreign key (post_id) references post(post_id);

create table job_type (
	job_type_id int auto_increment primary key,
    job_type_title varchar(100)
);
insert into job_type(job_type_title) values('In Office'),('Hybrid'),('Remote'),('Oversea');

create table post_job_type (
	post_id int,
    job_type_id int,
    primary key (post_id, job_type_id)
);
alter table post_job_type add constraint fk_type_id foreign key (job_type_id) references job_type(job_type_id);
alter table post_job_type add constraint fk_type_post_id foreign key (post_id) references post(post_id);

create table job_contract (
	contract_id int auto_increment primary key,
    contract_title varchar(100)
);
insert into job_contract(contract_title) values('Full time'),('Freelance'),('Part-time');

create table post_job_contract (
	post_id int,
    contract_id int,
    primary key (post_id, contract_id)
);
alter table post_job_contract add constraint fk_contract_id foreign key (contract_id) references job_contract(contract_id);
alter table post_job_contract add constraint fk_contract_post_id foreign key (post_id) references post(post_id);

create table post_save (
	user_id varchar(100),
    post_id int,
    primary key (user_id, post_id)
);
alter table post_save add constraint fk_candidate_post_save foreign key (user_id) references candidates (user_id);
alter table post_save add constraint fk_post_post_save foreign key (post_id) references post (post_id);

create table post_apply (
	user_id varchar(100),
    post_id int,
    primary key (user_id, post_id)
);
alter table post_apply add constraint fk_candidate_post_apply foreign key (user_id) references candidates (user_id);
alter table post_apply add constraint fk_post_post_apply foreign key (post_id) references post (post_id);
	
create table company_follow (
	candidate_id varchar(100),
    employer_id varchar(100),
    primary key (candidate_id, employer_id)
);
alter table company_follow add constraint fk_candidate_company_follow foreign key (candidate_id) references candidates (user_id);
alter table company_follow add constraint fk_employer_company_follow foreign key (employer_id) references employers (user_id);

create table candidate_save (
	employer_id varchar(100),
    candidate_id varchar(100),
    primary key (candidate_id, employer_id)
);
alter table candidate_save add constraint fk_employer_candidate_save foreign key (employer_id) references employers (user_id);
alter table candidate_save add constraint fk_candidate_candidate_save foreign key (candidate_id) references candidates (user_id);

create table candidate_apply (
	employer_id varchar(100),
    candidate_id varchar(100),
    primary key (candidate_id, employer_id)
);
alter table candidate_apply add constraint fk_employer_candidate_apply foreign key (employer_id) references employers (user_id);
alter table candidate_apply add constraint fk_candidate_candidate_apply foreign key (candidate_id) references candidates (user_id);

create table provinces (
	pro_id int auto_increment primary key,
    pro_name varchar(50)
);
insert into provinces (pro_name) values ('Ha Noi'), ('Da Nang'), ('Ho Chi Minh');

use jobSeeking;				
select * from roles;
select * from post;	
select * from post_status;
select * from users;
select * from employers;
select * from candidates;
select * from requirement_desc;
select * from avatar;
select * from job_level;
select * from job_type;
select * from job_contract;
select * from post_job_type;
select * from post_job_contract;
select * from post_job_level;
select * from provinces;

insert into users (user_id, email, password, role_id) value ('admin-111-222', 'admin@gmail.com', 'admin123', 1)

drop table users;
drop table candidates;
drop table post;
drop table employers;
drop table post_apply;
drop table post_save;
drop table requirement_desc;
drop table post_job_level;
drop table post_job_type;
drop table post_job_contract;
drop table company_follow;
drop table candidate_save;
drop table candidate_apply;

