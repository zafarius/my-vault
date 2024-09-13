CREATE TABLE "account"
(
    "id"                 UUID         NOT NULL,
    "username"           VARCHAR(100) NOT NULL,
    "password"           VARCHAR(255) NOT NULL,
    "version"            BIGINT       NOT NULL DEFAULT 0,
    "created_date"       TIMESTAMP    NOT NULL DEFAULT current_timestamp,

    -- PK/UK Constraints
    CONSTRAINT "PK_account" PRIMARY KEY ("id"),
    CONSTRAINT "UK_username" UNIQUE ("username")
);

