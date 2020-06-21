insert into users(id, cart, approved, blocked, email, first_name, last_name, business_number, company_name, address, authorities, password) values (1, 1, true, false, 'user', 'Joseph', 'Joestar', '1', '', 'some address', 'ROLE_CLIENT;UPDATE_CART;DELETE_FROM_CART;READ_CART;CREATE_BOOKING;ACCEPT_BOOKING;CANCEL_BOOKING;REJECT_BOOKING;RESEVE_BOOKING;CREATE_AD;ACTIVATE_AD;DEACTIVATE_AD;EDIT_AD;READ_CLIENT_ADS;CREATE_CAR;READ_CAR;EDIT_CAR;DELETE_CAR;READ_CLIENT_CARS;READ_BOOKINGS;READ_CLIENT_ADS', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');
insert into users(id, approved, blocked, email, first_name, last_name, business_number, company_name, address, authorities, password) values (2, true, false, 'agent', 'Agent', 'Agent', '2', '', 'some address', 'ROLE_COMPANY;READ_ADS;SEND_RENT_REQUEST;READ_USER_PRIVILEGES', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');
insert into users(id, approved, blocked, email, first_name, last_name, business_number, company_name, address, authorities, password) values (3, true, false, 'admin1', 'Johh', 'Doe', '3', '', 'some address', 'ROLE_ADMIN;READ_USER_PRIVILEGES;UPDATE_USER_PRIVILEGES;READ_CLIENTS;UPDATE_CLIENT_BLOCK;DELETE_CLIENT;ADD_AGENT;ADD_COMPANY;READ_CODEBOOK;UPDATE_CODEBOOK;READ_PENDING_COMMENTS;UPDATE_PENDING_COMMENTS;READ_BOOKINGS;READ_CLIENT_ADS;READ_ADS;READ_CARS;READ_CAR;READ_CLIENT_CARS', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');
insert into users(id, cart, approved, blocked, email, first_name, last_name, business_number, company_name, address, authorities, password) values (4, 2, true, false, 'user1', 'Johny', 'Joestar', '4', '', 'some address', 'ROLE_CLIENT;UPDATE_CART;DELETE_FROM_CART;READ_CART;CREATE_BOOKING;ACCEPT_BOOKING;CANCEL_BOOKING;REJECT_BOOKING;RESEVE_BOOKING;CREATE_AD;ACTIVATE_AD;DEACTIVATE_AD;EDIT_AD;READ_CLIENT_ADS;CREATE_CAR;READ_CAR;EDIT_CAR;DELETE_CAR;READ_CLIENT_CARS;READ_BOOKINGS;READ_CLIENT_ADS', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');
insert into users(id, approved, blocked, email, first_name, last_name, business_number, company_name, address, authorities, password) values (5, true, false, 'agent1', 'Agent1', 'Agent1', '5', '', 'some address', 'ROLE_COMPANY;READ_ADS;SEND_RENT_REQUEST;READ_USER_PRIVILEGES', '$2a$10$GuztdmZFDUgqX/FvWCMPdu/lUfkHxxs8bkJvw6IxvSJv3T54SU6ea');

alter sequence user_id_seq RESTART with 6;

-- ROLES
insert into role(id, name) values (1, 'ROLE_CLIENT');
insert into role(id, name) values (2, 'ROLE_COMPANY');
insert into role(id, name) values (3, 'ROLE_ADMIN');
insert into role(id, name) values (4, 'ROLE_AGENT');

-- PRIVILEGES
insert into privilege(id, name) values (1, 'READ_USER_PRIVILEGES');
insert into privilege(id, name) values (2, 'UPDATE_USER_PRIVILEGES');
insert into privilege(id, name) values (3, 'READ_CLIENTS');
insert into privilege(id, name) values (4, 'UPDATE_CLIENT_BLOCK');
insert into privilege(id, name) values (5, 'DELETE_CLIENT');
insert into privilege(id, name) values (6, 'ADD_AGENT');
insert into privilege(id, name) values (7, 'ADD_COMPANY');
insert into privilege(id, name) values (8, 'READ_CODEBOOK');
insert into privilege(id, name) values (9, 'UPDATE_CODEBOOK');
insert into privilege(id, name) values (10, 'READ_PENDING_COMMENTS');
insert into privilege(id, name) values (11, 'UPDATE_PENDING_COMMENTS');


-- cart privilege
insert into privilege(id, name) values (14, 'UPDATE_CART');
insert into privilege(id, name) values (15, 'DELETE_FROM_CART');
insert into privilege(id, name) values (16, 'READ_CART');

-- booking privilege
insert into privilege(id, name) values (17, 'CREATE_BOOKING');
insert into privilege(id, name) values (18, 'ACCEPT_BOOKING');
insert into privilege(id, name) values (19, 'CANCEL_BOOKING');
insert into privilege(id, name) values (20, 'REJECT_BOOKING');
insert into privilege(id, name) values (21, 'READ_BOOKINGS');
insert into privilege(id, name) values (22, 'RESERVE_BOOKING');
insert into privilege(id, name) values (23, 'SEND_RENT_REQUEST');

--ad privilege
insert into privilege(id, name) values (24, 'CREATE_AD');
insert into privilege(id, name) values (25, 'ACTIVATE_AD');
insert into privilege(id, name) values (26, 'DEACTIVATE_AD');
insert into privilege(id, name) values (27, 'EDIT_AD');
insert into privilege(id, name) values (28, 'READ_CLIENT_ADS');
insert into privilege(id, name) values (29,  'READ_ADS');

--car
insert into privilege(id, name) values (30, 'CREATE_CAR');
insert into privilege(id, name) values (31, 'READ_CARS');
insert into privilege(id, name) values (32, 'READ_CAR');
insert into privilege(id, name) values (33, 'EDIT_CAR');
insert into privilege(id, name) values (34, 'DELETE_CAR');
insert into privilege(id, name) values (35, 'READ_CLIENT_CARS');

alter sequence role_id_seq RESTART with 5;
alter sequence privilege_id_seq RESTART with 36;




-- ADMIN PRIVILEGE DEFAULT
insert into role_privilege(role_id, privilege_id) values (3, 1);
insert into role_privilege(role_id, privilege_id) values (3, 2);
insert into role_privilege(role_id, privilege_id) values (3, 5);
insert into role_privilege(role_id, privilege_id) values (3, 6);
insert into role_privilege(role_id, privilege_id) values (3, 7);
insert into role_privilege(role_id, privilege_id) values (3, 8);
insert into role_privilege(role_id, privilege_id) values (3, 9);
insert into role_privilege(role_id, privilege_id) values (3, 10);
insert into role_privilege(role_id, privilege_id) values (3, 11);
insert into role_privilege(role_id, privilege_id) values (3, 21);
insert into role_privilege(role_id, privilege_id) values (3, 28);
insert into role_privilege(role_id, privilege_id) values (3, 29);
insert into role_privilege(role_id, privilege_id) values (3, 31);
insert into role_privilege(role_id, privilege_id) values (3, 32);
insert into role_privilege(role_id, privilege_id) values (3, 35);


--CLIENT PRIVILEGE DEFAULT
insert into role_privilege(role_id, privilege_id) values (1, 14);
insert into role_privilege(role_id, privilege_id) values (1, 15);
insert into role_privilege(role_id, privilege_id) values (1, 16);
insert into role_privilege(role_id, privilege_id) values (1, 17);
insert into role_privilege(role_id, privilege_id) values (1, 18);
insert into role_privilege(role_id, privilege_id) values (1, 19);
insert into role_privilege(role_id, privilege_id) values (1, 20);
insert into role_privilege(role_id, privilege_id) values (1, 21);
insert into role_privilege(role_id, privilege_id) values (1, 22);
insert into role_privilege(role_id, privilege_id) values (1, 23);
insert into role_privilege(role_id, privilege_id) values (1, 24);
insert into role_privilege(role_id, privilege_id) values (1, 25);
insert into role_privilege(role_id, privilege_id) values (1, 26);
insert into role_privilege(role_id, privilege_id) values (1, 27);
insert into role_privilege(role_id, privilege_id) values (1, 28);
insert into role_privilege(role_id, privilege_id) values (1, 30);
insert into role_privilege(role_id, privilege_id) values (1, 32);
insert into role_privilege(role_id, privilege_id) values (1, 33);
insert into role_privilege(role_id, privilege_id) values (1, 34);
insert into role_privilege(role_id, privilege_id) values (1, 35);


insert into user_cars(user_id, car_id) values (1, 1);
insert into user_cars(user_id, car_id) values (1, 2);
insert into user_cars(user_id, car_id) values (1, 3);
insert into user_cars(user_id, car_id) values (4, 4);