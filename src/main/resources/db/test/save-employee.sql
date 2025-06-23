INSERT INTO employees(`name`, dni, phone_number, email, `address`, registration_date, active_user) value 
    ("Julio Barrientos", "65467866365", "+50499687932", "lkdsjlfasd@gmail.com", "oweuroidjsljfaoiue", "2011-5-08", 1);

INSERT INTO users(email, username, `password`) value ("lskdajfls@gmail.com", "julio.barrientos", "$2a$12$3S50A80yntl2nzBffkpJt.SDyAaGiLzjeZysYDWnECfWVPhzTSAp.");

INSERT INTO employee_departaments_positions(id_employee, id_departament, id_position) values (1, 1, 2), (1, 1, 1);

INSERT INTO user_roles(id_user, id_role) value (2,2);
