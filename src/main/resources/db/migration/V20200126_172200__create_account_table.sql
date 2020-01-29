create table account
(
    id          UUID NOT NULL,
    name        VARCHAR NOT NULL,
    balance     DECIMAL NOT NULL,
    PRIMARY KEY (id)
);