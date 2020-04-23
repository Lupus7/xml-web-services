insert into users(id, approved, blocked, email, password) values (1, true, false, 'user1', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');
insert into users(id, approved, blocked, email, password) values (2, true, false, 'company1', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');
insert into users(id, approved, blocked, email, password) values (3, true, false, 'admin1', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');

ALTER SEQUENCE user_id_seq RESTART WITH 4;

insert into role(id, name) values (1, 'ROLE_USER');
insert into role(id, name) values (2, 'ROLE_COMPANY');
insert into role(id, name) values (2, 'ROLE_ADMIN');

insert into user_role(user_id, role_id) values (1, 1);
insert into user_role(user_id, role_id) values (2, 2);
insert into user_role(user_id, role_id) values (3, 3);


