insert into users(id, approved, blocked, email, first_name, last_name, company_name, address, authorities, password) values (1, true, false, 'user1', 'Johh', 'Doe', '', 'some address', 'ROLE_USER;READ_ADS;SEND_RENT_REQUEST;READ_USER_PRIVILEGES', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');
insert into users(id, approved, blocked, email, first_name, last_name, company_name, address, authorities, password) values (2, true, false, 'company1', '', '', 'some company name', 'some address', 'ROLE_COMPANY;READ_ADS;SEND_RENT_REQUEST;READ_USER_PRIVILEGES', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');
insert into users(id, approved, blocked, email, first_name, last_name, company_name, address, authorities, password) values (3, true, false, 'admin1', 'Johh', 'Doe', '', 'some address', 'ROLE_ADMIN;READ_ADS;READ_USER_PRIVILEGES;SEND_RENT_REQUEST', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');

ALTER SEQUENCE user_id_seq RESTART WITH 4;

insert into role(id, name) values (1, 'ROLE_USER');
insert into role(id, name) values (2, 'ROLE_COMPANY');
insert into role(id, name) values (2, 'ROLE_ADMIN');

insert into privilege(id, name) values (1, 'READ_USER_PRIVILEGES');
insert into privilege(id, name) values (2, 'UPDATE_USER_PRIVILEGES');
insert into privilege(id, name) values (3, 'READ_ADS');
insert into privilege(id, name) values (4, 'SEND_RENT_REQUEST');

insert into role_privilege(role_id, privilege_id) values (1, 3);
insert into role_privilege(role_id, privilege_id) values (1, 4);
insert into role_privilege(role_id, privilege_id) values (3, 1);
insert into role_privilege(role_id, privilege_id) values (3, 2);


