INSERT INTO authors (name)
VALUES ('Robert Martin'),
       ('Kent Beck'),
       ('Michael C. Feathers');

INSERT INTO books(name, price, publication_year, author_id)
VALUES
('Clean Code', 50, 2008, 1),
('The Clean Coder', 50, 2011, 1),
('Clean Architecture', 50, 2017, 1),
('Test Driven Development by Example', 50, 2003, 2),
('Working Effectively With Legacy Code', 50, 2004, 3);