create table transferdetails
(
    id                        UUID                                                             NOT NULL,
    receiver_id               UUID                                                             NOT NULL,
    sender_id                 UUID                                                             NOT NULL,
    amount                    DECIMAL                                                          NOT NULL,
    status                    VARCHAR                                                          NOT NULL,
    created_on                TIMESTAMP WITH TIME ZONE                                         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT transferdetails_receiver_id_fk   FOREIGN KEY (receiver_id) REFERENCES account (id),
    CONSTRAINT transferdetails_sender_id_fk        FOREIGN KEY (sender_id) REFERENCES account (id),
    CONSTRAINT amount_check CHECK (amount > 0)
);