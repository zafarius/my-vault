-- userTest1
INSERT INTO "vault"."account" ("id", "username", "password") VALUES ('4b085d51-b364-41b5-a4e2-c25dac3b7a4a','userTest1', '$2a$10$VJKgN6V6yfCgo9X50f3Yx.6etxCRL6cf9oV2cQ5Xv.AD1ji3vOioW');
INSERT INTO "vault"."account_roles" ("account_id", "role_name") VALUES ('4b085d51-b364-41b5-a4e2-c25dac3b7a4a', 'ADMIN');
INSERT INTO "vault"."account_roles" ("account_id", "role_name") VALUES ('4b085d51-b364-41b5-a4e2-c25dac3b7a4a', 'USER');

-- userTest2
INSERT INTO "vault"."account" ("id", "username", "password") VALUES ('4b085d51-b364-41b5-a4e2-c25dac3b7a4b','userTest2', '$2a$10$VJKgN6V6yfCgo9X50f3Yx.6etxCRL6cf9oV2cQ5Xv.AD1ji3vOioW');
INSERT INTO "vault"."account_roles" ("account_id", "role_name") VALUES ('4b085d51-b364-41b5-a4e2-c25dac3b7a4b', 'USER');