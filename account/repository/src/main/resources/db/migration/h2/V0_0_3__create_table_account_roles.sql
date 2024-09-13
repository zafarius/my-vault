CREATE TABLE "account_roles"
(
    "account_id"           UUID NOT NULL,
    "role_name"          VARCHAR(255) NOT NULL,

    FOREIGN KEY ("role_name") REFERENCES "roles" ("role_name"),
    FOREIGN KEY ("account_id") REFERENCES "account" ("id")
);

