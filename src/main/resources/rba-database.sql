-- CRUD - Create, Read, Update, Delete (soft/hard)


DROP TABLE members; -- hard delete
drop table accounts;

-- MEMBER TABLE
CREATE TABLE members(
	user_id serial primary key not null,
	first_name VARCHAR(20),
	last_name VARCHAR(40),
	email VARCHAR(50) UNIQUE NOT NULL,
	password VARCHAR(64) NOT null,
	member_type member_enum_type default 'USER'
);

create type member_enum_type as enum('ADMIN', 'BANKER', 'USER');

insert into members(first_name, email, "password") values('admin', 'luisalejandro367@revature.net', 'password');

select * from members
	where email='luisalejandro367@revature.net'; -- i would use first_name='admin' BUT what if someone decides to make their name admin. email seems more secure since it's unique.

update members set member_type='ADMIN', email='admin@email.com'
	where email='luisalejandro367@revature.net';

insert into members(first_name, last_name, email, "password")
	values('John', 'Doe', 'user@email.com', 'password');

select * from members;

-- ACCOUNT TABLE
create type account_enum_type as enum('CHECKING', 'SAVINGS', 'JOINT');

create table accounts(
	routing_number integer primary key not null unique check(length(cast(routing_number as varchar(9))) = 9) ,
	user_id int references members(user_id),
	account_type account_enum_type default 'CHECKING',
	account_name varchar(30),
	amount decimal(10, 2) default 0.00
);

alter table accounts 
	add constraint fk_user_id foreign key (user_id) 
	references members(user_id);

insert into accounts
	values(123456789, 2, default, 'My checking account', default);

select * from accounts;