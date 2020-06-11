-- brands
insert into brand(id, name) values (1, 'Opel');
insert into brand(id, name) values (2, 'Audi');
insert into brand(id, name) values (3, 'BMW');
insert into brand(id, name) values (4, 'Fiat');
insert into brand(id, name) values (5, 'Ford');
insert into brand(id, name) values (6, 'Honda');
insert into brand(id, name) values (7, 'Toyota');

alter sequence brand_id_seq RESTART with 8;


-- models
insert into model(id, name, brand_id) values (1, 'Astra', 1);
insert into model(id, name, brand_id) values (2, 'Zafira', 1);
insert into model(id, name, brand_id) values (3, 'Vectra', 1);

insert into model(id, name, brand_id) values (4, 'A6', 2);
insert into model(id, name, brand_id) values (5, 'Q3', 2);
insert into model(id, name, brand_id) values (6, 'R8', 2);

insert into model(id, name, brand_id) values (7, 'I8', 3);
insert into model(id, name, brand_id) values (8, 'X6', 3);
insert into model(id, name, brand_id) values (9, 'Z7', 3);

insert into model(id, name, brand_id) values (10, 'Panda', 4);
insert into model(id, name, brand_id) values (11, 'Punto', 4);
insert into model(id, name, brand_id) values (12, 'Stilo', 4);

insert into model(id, name, brand_id) values (13, 'Granada', 5);
insert into model(id, name, brand_id) values (14, 'Fiesta', 5);
insert into model(id, name, brand_id) values (15, 'Taunus', 5);

insert into model(id, name, brand_id) values (16, 'Accord', 6);
insert into model(id, name, brand_id) values (17, 'Civic', 6);
insert into model(id, name, brand_id) values (18, 'Legend', 6);

insert into model(id, name, brand_id) values (19, 'Corona', 7);
insert into model(id, name, brand_id) values (20, 'Dyna', 7);
insert into model(id, name, brand_id) values (21, 'Prius', 7);

alter sequence model_id_seq RESTART with 22;


-- car Classes
insert into class(id, name) values (1, 'Microcar');
insert into class(id, name) values (2, 'Minicompact');
insert into class(id, name) values (3, 'Subcompact');
insert into class(id, name) values (4, 'Compact');
insert into class(id, name) values (5, 'Mid-size');
insert into class(id, name) values (6, 'Full-size');
insert into class(id, name) values (7, 'Minivan');
insert into class(id, name) values (8, 'Sports car');
insert into class(id, name) values (9, 'Sports sedan');
insert into class(id, name) values (10, 'Supercar');
insert into class(id, name) values (11, 'Caravan');

alter sequence car_class_id_seq RESTART with 12;

-- fuels
insert into fuel(id, name) values (1, 'Gasoline');
insert into fuel(id, name) values (2, 'Diesel');
insert into fuel(id, name) values (3, 'Liquified Petroleum');
insert into fuel(id, name) values (4, 'Compressed Natural Gas');
insert into fuel(id, name) values (5, 'Ethanol');
insert into fuel(id, name) values (6, 'Bio-Diesel');

alter sequence fuel_id_seq RESTART with 7;


-- transmissions
insert into transmission(id, name) values (1, 'Automatic');
insert into transmission(id, name) values (2, 'Manual');

alter sequence transmission_id_seq RESTART with 3;

-- brand->models
insert into brand_models(brand_id, models_id) values (1,1);
insert into brand_models(brand_id, models_id) values (1,2);
insert into brand_models(brand_id, models_id) values (1,3);
insert into brand_models(brand_id, models_id) values (2,4);
insert into brand_models(brand_id, models_id) values (2,5);
insert into brand_models(brand_id, models_id) values (2,6);
insert into brand_models(brand_id, models_id) values (3,7);
insert into brand_models(brand_id, models_id) values (3,8);
insert into brand_models(brand_id, models_id) values (3,9);
insert into brand_models(brand_id, models_id) values (4,10);
insert into brand_models(brand_id, models_id) values (4,11);
insert into brand_models(brand_id, models_id) values (4,12);
insert into brand_models(brand_id, models_id) values (5,13);
insert into brand_models(brand_id, models_id) values (5,14);
insert into brand_models(brand_id, models_id) values (5,15);
insert into brand_models(brand_id, models_id) values (6,16);
insert into brand_models(brand_id, models_id) values (6,17);
insert into brand_models(brand_id, models_id) values (6,18);
insert into brand_models(brand_id, models_id) values (7,19);
insert into brand_models(brand_id, models_id) values (7,20);
insert into brand_models(brand_id, models_id) values (7,21);
