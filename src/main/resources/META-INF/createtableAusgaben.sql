DROP TABLE IF EXISTS ausgabe CASCADE;
CREATE TABLE IF NOT EXISTS ausgabe
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    date varchar(50) DEFAULT NULL,
    bemerkung varchar(50) DEFAULT NULL,
    betrag float       DEFAULT NULL
);
