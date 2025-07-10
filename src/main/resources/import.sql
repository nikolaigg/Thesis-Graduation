INSERT INTO role(authority) VALUES ('ROLE_ADMIN'), ('ROLE_TEACHER'), ('ROLE_STUDENT');

INSERT INTO app_user(account_non_expired, account_non_locked, credentials_non_expired, enabled, profile_id, password, username) VALUES (true,true,true,true,null,'$2a$12$.Sy7QoFjxvvZ9cl02u9gOOLkBWDC7AWWRRi/AgiTODegrsLC3lBAa','admin');

INSERT INTO role_users(role_id, user_id) VALUES (1,1);