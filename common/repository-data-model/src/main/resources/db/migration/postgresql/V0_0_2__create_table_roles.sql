CREATE TABLE "roles"
(
    "role_name"          VARCHAR(255) NOT NULL primary key,
    "version"            BIGINT       NOT NULL DEFAULT 0
);

INSERT INTO "roles" ("role_name") VALUES ('ADMIN');
INSERT INTO "roles" ("role_name") VALUES ('USER');
