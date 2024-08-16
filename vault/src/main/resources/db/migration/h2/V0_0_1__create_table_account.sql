CREATE TABLE "account"
(
    "username"           VARCHAR(100) NOT NULL primary key,
    "password"           VARCHAR(255) NOT NULL,
    "version"            BIGINT       NOT NULL DEFAULT 0
);

