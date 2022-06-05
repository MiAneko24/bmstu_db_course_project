--drop schema public cascade;
create schema public;

create domain system_type as text
not null check (
	value = 'Sugeno' or value = 'Mamdani'
);


create domain specialization_type as text
not null check 
(
	value = 'physics' or value = 'chemistry' or value = 'informatics'
);

create table system (
	s_id int primary key,
	s_name text unique,
	s_type system_type not null,
	specialization specialization_type
);

insert into system 
values(1, 'Контроль температуры', 'Mamdani', 'physics');


create table variable (
	v_id int primary key,
	v_name text not null,
	min_value real not null,
	max_value real not null,
	v_value real null check(v_value between min_value and max_value),
	s_id int not null references system(s_id) on delete cascade
);


create domain m_function_type as text
not null check
(
	value = 'trapezoidal' or value = 'triangle' or value = 'shoulder'
	or value = 'linguistic' or value = 'gauss' or value = 'crisp' 
	or value = 'linear'
);

create domain linguistic_barrier as text
check 
(
	value = 'Very' or value = 'More or less' or value = 'Plus' or value = 'Not'
	or value = 'Not very' or value = null
);

create table membership_function
(
	m_id int primary key,
	term text check (term is not null or m_type = 'linear' or m_type = 'crisp'),
	m_type m_function_type,
	v_id int references variable(v_id) on delete cascade check((m_type = 'linear' and v_id is not null) or (m_type != 'linear')),
	parameter1 real check (m_type != 'linguistic' or null),
	parameter2 real check (((m_type = 'trapezoidal' or m_type = 'triangle' or m_type = 'shoulder') 
		and parameter2 > parameter1) or m_type = 'gauss' or null),
	parameter3 real check (((m_type = 'trapezoidal' or m_type = 'triangle') 
		and parameter3 > parameter2) or m_type = 'shoulder' or null),
	parameter4 real check (m_type = 'trapezoidal' and parameter4 > parameter3 or null),
	m_value real check (m_value between 0 and 1 or m_type = 'linear' or m_type = 'crisp'),
	p_id int references membership_function(m_id) on delete cascade,
	barrier linguistic_barrier check ((m_type = 'lingustic' and barrier != null) or null),
	is_active boolean default true
);

create domain ant_connection_type as text
check
(
	value = 'or' or value = 'and'
);

create table rule (
	r_id int primary key,
	s_id int references system(s_id) on delete cascade,
	antecedent_connection ant_connection_type,
	weight real not null default 1
);

create table antecedent (
	a_id int primary key,
	m_id int references membership_function(m_id) on delete cascade
);

create table consequent (
	c_id int primary key,
	m_id int references membership_function(m_id) on delete cascade,
	r_id int references rule(r_id) on delete cascade,
	v_id int references variable(v_id) on delete cascade
);

create table rule_antecedents (
	r_id int references rule(r_id) on delete cascade,
	a_id int references antecedent(a_id) on delete cascade,
	primary key (r_id, a_id)
);

create or replace function trapezoidal(value real, a real, b real, c real, d real)
returns real 
as $$
declare 
	res real;
begin
	if (value between a and b) 
	then 
		res := (value - a) / (b - a);
	else 
		if (value between b and c)
		then res := 1;
		else
			if (value between c and d)
			then 
				res := (d - value) / (d - c);
			else 
				res := 0;
			end if;
		end if;
	end if;
	return res;
end;
$$ language plpgsql;


create or replace function triangle(value real, a real, b real, c real)
returns real 
as $$
declare 
	res real;
begin
	if (value between a and b)
	then 
		res := (value - a) / (b - a);
	else 
		if (value between b and c)
		then 
			res := (c - value) / (c - b);
		else
			res := 0;
		end if;
	end if;
	return res;
end;
$$ language plpgsql;


create or replace function shoulder(value real, a real, b real, g real)
returns real 
as $$
declare 
	res real;
	rev bool;
begin
	if (g < a)
		then select 1 - shoulder from shoulder(value, g, b, a) into res;
	else
		if (value <= a)
		then 
			res := 0;
		else
			if (value <= b)
			then 
				res := 2 * ((value - a) / (g - a)) ^ 2;
			else 
				if (value <= g)
				then
					res := 1 - 2 * ((value - g) / (g - a)) ^ 2;
				else 
					res := 1;
				end if;
			end if;
		end if;
--	if (rev)
--		then 
--			res := 1 - res;
	end if;
	return res;	
end
$$ language plpgsql;

create or replace function gauss(value real, g real, b real)
returns real 
as $$
declare 
	res real;
begin
	if (value <= g)
	then 
		select * from shoulder(value, g - b, (g - b / 2)::real, g) into res;
	else 
		select * from shoulder(value, g, (g + b / 2)::real, g + b) into res;
		res := 1 - res;
	end if;
	return res;
end
$$ language plpgsql;

select * from gauss(26::real, 6::real, 21::real);

create or replace function linguistic(v real, barrier linguistic_barrier, pid int)
returns real 
as $$
declare 
	res real;
begin
	select case m_type
		when 'trapezoidal' then (select * from trapezoidal(v, parameter1, parameter2, parameter3, parameter4))
		when 'triangle' then (select * from triangle(v, parameter1, parameter2, parameter3))
		when 'shoulder' then (select * from shoulder(v, parameter1, parameter2, parameter3))
		when 'gauss' then (select * from gauss(v, parameter1, parameter2))
		when 'crisp' then parameter1
		when 'linguistic' then (select mf2.m_value from membership_function mf2 where mf2.m_id = pid)
		end
	from membership_function mf1
	where m_id = pid 
	into res;
	select case barrier 
		when 'Very'
			then res ^ 2
		when 'More or less'
			then res ^ 0.5
		when 'Plus'
			then res ^ 1.25
		when 'Not'
			then 1 - res
		when 'Not very'
			then 1 - res ^ 2
		else 0
		end
	into res;
	return res;
end;
$$ language plpgsql;

--
create or replace function update_membership()
returns trigger
as $$
begin
	update membership_function 
	set m_value = case m_type 
				when 'trapezoidal' then (select * from trapezoidal(new.v_value, parameter1, parameter2, parameter3, parameter4))
				when 'triangle' then (select * from triangle(new.v_value, parameter1, parameter2, parameter3))
				when 'shoulder' then (select * from shoulder(new.v_value, parameter1, parameter2, parameter3))
				when 'gauss' then (select * from gauss(new.v_value, parameter1, parameter2))
				when 'linguistic' then (select * from linguistic(new.v_value, barrier, p_id))
				when 'linear' then parameter1 * new.v_value
		end
	where v_id = new.v_id;
	return new;
end
$$ language plpgsql;

drop trigger if exists update_funcs on variable ;
create trigger update_funcs
after update on variable 
for row execute procedure update_membership();

create or replace function membership_func_check()
returns trigger
as $$
begin 
	if (new.m_type = 'crisp')
	then
		new.m_value := new.parameter1;
	end if;
	return new;
end
$$ language plpgsql;

create trigger insert_mf
before insert on membership_function
for row execute procedure membership_func_check();
---------------------------------------------------------------

select * from "system" s;

insert into variable values 
(1, 'Temperature', -60, 70, null, 1),
(2, 'Pressure', 0, 100, null, 1);
select * from variable;
insert into membership_function values
--(1, 'Not Cold', 'shoulder', 'Temperature', -20, -7, 5, null, null, null, null),
(2, 'Cold', 'shoulder', 1, 5, -7.5, -20, null, null, null, null),
(3, 'Very cold', 'linguistic', 1, null, null, null, null, null, 2, 'Very'),
(4, 'Neutral', 'gauss', 1, 15, 10, null, null, null, null, null),
(5, 'Warm', 'trapezoidal', 1, 7, 20, 26, 30, null, null, null),
(6, 'A bit hot', 'triangle', 1, 24, 28, 32, null, null, null, null),
(7, 'Hot', 'shoulder', 1, 25, 32.5, 40, null, null, null, null),
(9, 'More or less hot', 'linguistic', 1, null, null, null, null, null, 7, 'More or less'),
(10, 'Not very warm', 'linguistic', 1, null, null, null, null, null, 5, 'Not very'),
(11, 'Plus neutral', 'linguistic', 1, null, null, null, null, null, 4, 'Plus'),
(12, 'Cons', 'linear', 1, 0.8, null, null, null, null, null, null);
insert into membership_function values
(13, 'Cons', 'crisp', null, 9.2);

update variable set v_value = 9 where v_name = 'Temperature';
--update variable set value = 
select * from variable;
select term, m_value from membership_function mf ;
--create or replace procedure add_rule(rule_text text) 

--Экология с: При стационарном состоянии экологических фактором, лимитирующем будет тот из них, Значение которго близко к необходимому минимуму 
--Любой живой организм имеет определенные верхний и нижний пределы устойчивости (толерантности) к любому экологическому фактору
