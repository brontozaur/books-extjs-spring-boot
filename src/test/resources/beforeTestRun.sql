insert into autor(autor_id, data_nasterii, nume) values (1, '2016-02-08', 'Savatie Bastovoi');
insert into editura(id_editura, nume_editura) values (1, 'Cathisma');
insert into categorie(id_categorie, nume_categorie) values (1, 'Carte ortodoxa');

insert into autor(autor_id, data_nasterii, nume) values (2, '1893-02-08', 'Karl May');
insert into editura(id_editura, nume_editura) values (2, 'Cartile adevarul');
insert into categorie(id_categorie, nume_categorie) values (2, 'Western');

insert into book (book_id, an_aparitie, citita, height, isbn, nr_pagini,
  original_title, serie, title, width, id_autor, book_cover_id, id_categorie, id_editura) values
  (1, 1996, 1, 130, '978-323-43222', 245, 'A iubi inseamna a ierta', 'SB', 'A iubi inseamna a ierta', 100, 1, NULL, 1, 1);