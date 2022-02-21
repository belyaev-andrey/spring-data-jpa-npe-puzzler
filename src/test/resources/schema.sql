
CREATE TABLE author
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_author PRIMARY KEY (id)
);

CREATE TABLE book
(
    title     VARCHAR(255),
    id        BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    CONSTRAINT pk_book PRIMARY KEY (id, author_id)
);

ALTER TABLE book
    ADD CONSTRAINT FK_BOOK_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES author (id);