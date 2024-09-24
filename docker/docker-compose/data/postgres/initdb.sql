CREATE USER "vault-user" WITH PASSWORD 'vaultMaster123';
CREATE DATABASE "vault" OWNER "vault-user";
GRANT ALL PRIVILEGES ON DATABASE "vault" TO "vault-user";