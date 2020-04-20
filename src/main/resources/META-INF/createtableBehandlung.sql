DROP TABLE IF EXISTS behandlung CASCADE;
CREATE TABLE IF NOT EXISTS behandlung (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  date date DEFAULT NULL,
  angebotid INTEGER DEFAULT NULL,
  anzahl INTEGER DEFAULT 0,
  summe float DEFAULT 0,
  FOREIGN KEY (angebotid) REFERENCES angebot(id)
);

