-- CRUD - Create, Read, Update, Delete (soft/hard)


DROP TABLE members; -- hard delete

CREATE TABLE members(
	user_id Integer primary key not null check (user_id > 99999999 and user_id < 1000000000),
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(40) NOT NULL,
	email VARCHAR(50) UNIQUE NOT NULL,
	password VARCHAR(64) NOT NULL
);

insert into members values(123456789,'Luis', 'Cruz', 'luisalejandro367@revature.net', 'password');

select * from members;