
-- cart
insert into cart(id, user_id) values (1, 1);
insert into cart(id, user_id) values (2, 2);
insert into cart(id, user_id) values (3, 3);
insert into cart(id, user_id) values (4, 4);
insert into cart(id, user_id) values (5, 5);
alter sequence cart_id_seq RESTART with 6;

insert into booking(id, start_date, end_date, state, place, created, ad, loaner, bundle_id) values (1, '2020-09-19T00:00:00', '2020-09-30T00:00:00', 3, 'Novi Sad', '2020-09-19T00:00:00', 6, 1, null);
insert into booking(id, start_date, end_date, state, place, created, ad, loaner, bundle_id) values (2, '2020-09-19T00:00:00', '2020-09-30T00:00:00', 3, 'Novi Sad', '2020-09-19T00:00:00', 7, 1, null);
insert into booking(id, start_date, end_date, state, place, created, ad, loaner, bundle_id) values (3, '2020-09-19T00:00:00', '2020-09-30T00:00:00', 3, 'Novi Sad', '2020-09-19T00:00:00', 8, 1, null);
insert into booking(id, start_date, end_date, state, place, created, ad, loaner, bundle_id) values (4, '2020-09-19T00:00:00', '2020-09-30T00:00:00', 3, 'Novi Sad', '2020-09-19T00:00:00', 9, 1, null);
alter sequence booking_id_seq RESTART with 5;






