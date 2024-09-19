CREATE TABLE "file"
(
    "id"                 UUID         NOT NULL,
    "name"               VARCHAR(255) NOT NULL,
    "type"               VARCHAR(50)  NOT NULL,
    "path"               VARCHAR(510) NOT NULL,
    "created_date"       TIMESTAMP    NOT NULL DEFAULT current_timestamp,
    "size"               BIGINT       NOT NULL,
    "account_id"         UUID         NOT NULL,

    -- PK/UK Constraints
    CONSTRAINT "PK_file" PRIMARY KEY ("id"),
    FOREIGN KEY ("account_id") REFERENCES "account" ("id")
);

