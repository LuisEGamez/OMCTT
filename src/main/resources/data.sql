INSERT INTO omc.address (city, country, street, zipcode) values ("Granada", "Spain", "Lorca", "21232");
INSERT INTO omc.address (city, country, street, zipcode) values ("Malaga", "Spain", "Canal", "23456");
INSERT INTO omc.address (city, country, street, zipcode) values ("Almeria", "Spain", "Victoria", "45362");
INSERT INTO omc.address (city, country, street, zipcode) values ("Barcelona", "Spain", "Llull", "08005");
INSERT INTO omc.address (city, country, street, zipcode) values ("Madrid", "Spain", "Francisco I", "32123");
INSERT INTO omc.address (city, country, street, zipcode) values ("Vigo", "Spain", "Pulpo", "56789");
INSERT INTO omc.users (name, username, password, address_id) values ("Luis", "luis23", "12345", 1);
INSERT INTO omc.users (name, username, password, address_id) values ("Maria", "maria12", "12345", 2);
INSERT INTO omc.users (name, username, password, address_id) values ("Lucas", "lucas22", "12345", 3);
INSERT INTO omc.users (name, username, password, address_id) values ("Ana", "ana23", "12345", 4);
INSERT INTO omc.users (name, username, password, address_id) values ("Juan", "juan55", "12345", 5);
INSERT INTO omc.todos (title, completed, user_id) values ("Comprar comida", FALSE, 1);
INSERT INTO omc.todos (title, completed, user_id) values ("Lavar platos", TRUE, 1);
INSERT INTO omc.todos (title, completed, user_id) values ("Cortar setos", FALSE, 3);

