CREATE TABLE "account_roles"
(
    "username"           VARCHAR(100) NOT NULL,
    "role_name"          VARCHAR(255) NOT NULL,

    FOREIGN KEY ("role_name") REFERENCES "roles" ("role_name"),
    FOREIGN KEY ("username") REFERENCES "account" ("username")
);

