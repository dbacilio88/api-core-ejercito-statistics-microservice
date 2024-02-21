DROP TABLE IF EXISTS EP_USER;

CREATE TABLE IF NOT EXISTS EP_USER
(
    ES_ID   NUMERIC(38, 0) NOT NULL,
    ES_UUID uuid           NOT NULL,
    ES_NAME VARCHAR(50)    NOT NULL,
    CONSTRAINT EP_USER_PK PRIMARY KEY (ES_ID),
    CONSTRAINT EP_USER_UN1 UNIQUE(ES_UUID)

);