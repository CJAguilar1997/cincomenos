insert into users(email, password, username) value ("teodoro@gmail.com", "$2a$12$uoPPjYrPMmHrO6KFhMEUDuVsDFVTS3pwK7tp4VaPPOfX.6nP21zXu", "teodoro-gonzales");

insert into roles(role) values ("OWNER"), ("ADMIN");

insert into user_roles(id_user, id_role) value (1, 1);