create table users (
	user_id serial primary key,
	username varchar(50) unique not null
);

create table projects (
	project_id serial primary key,
	owner_id integer references users(user_id) on delete cascade,
	project_name varchar(50) unique not null,
	client_name varchar(50) unique
);

