ALTER TABLE books
    ADD COLUMN author_name varchar(255);

UPDATE books b SET author_name = (SELECT name FROM authors au WHERE au.id = b.author_id) WHERE b.author_name IS NULL;

ALTER TABLE books
DROP COLUMN author_id;

DROP TABLE authors;
