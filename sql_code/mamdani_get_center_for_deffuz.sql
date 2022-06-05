create or replace function trapezoidal_get_start(degree real, a real, b real, c real, d real)
returns real
as $$
declare 
	res real;
	tmp_res real;
begin
	if (abs(degree) < 1e-5)
		then 
			res := a;
	else 
		if (abs(degree - 1) < 1e-5)
			then
				res := b;
		else 
			res := degree * (b - a) + a;
		end if;
	end if;
	return res;
end
$$ language plpgsql;


create or replace function trapezoidal_get_end(degree real, a real, b real, c real, d real)
returns real
as $$
declare 
	res real;
begin
	if (abs(degree) < 1e-5)
		then 
			res := d;
	else 
		if (abs(degree - 1) < 1e-5)
			then
				res := c;
		else 
			res := d - degree * (d - c);
		end if;
	end if;
	return res;
end
$$ language plpgsql;


select trapezoidal_get_start(0.15384616, mf.parameter1, mf.parameter2, mf.parameter3, mf.parameter4),
	trapezoidal_get_end(0.15384616, mf.parameter1, mf.parameter2, mf.parameter3, mf.parameter4)
	from membership_function mf 
	where m_type = 'trapezoidal';

create or replace function triangle_get_start(degree real, a real, b real, c real)
returns real
as $$
declare 
	res real;
begin
	if (abs(degree) < 1e-5)
	then
		res := a;
	else
		res := degree * (b - a) + a;
	end if;
	return res;
end
$$ language plpgsql;


create or replace function triangle_get_end(degree real, a real, b real, c real)
returns real
as $$
declare 
	res real;
begin
	if (abs(degree) < 1e-5)
	then
		res := c;
	else
		res := c - degree * (c - b);
	end if;
	return res;
end
$$ language plpgsql;

create or replace function shoulder_get_start(degree real, a real, b real, g real, var int)
returns real 
as $$
declare 
	res real;
	tmp_res real;
begin
	select min_value from variable where v_id = var into res;
	if (a < g)
		then begin
			if (abs(degree) < 1e-5)
				then tmp_res := a;
			else 
				if (abs(degree - 1) < 1e-5) 
					then tmp_res := g;
				else 
					if (degree <= 0.5)
						then tmp_res := sqrt(degree / 2) * (g - a) + a;
					else 
						tmp_res := g - (g - a) * sqrt((1 - degree) / 2);
					end if;
				end if;
			end if;
			if (tmp_res > res)
				then res := tmp_res;
			end if;
		end;
	end if;
	return res;
end;
$$ language plpgsql;



create or replace function shoulder_get_end(degree real, a real, b real, g real, var int)
returns real 
as $$
declare 
	res real;
begin
	if (a > g)
		then select shoulder_get_start((1 - degree)::real, g, b, a, var) into res;
	else 
		select max_value from variable where v_id = var into res;
	end if;
	return res;
end;
$$ language plpgsql;


drop function gauss_get_start;
create or replace function gauss_get_start(degree real, g real, b real, var int)
returns real 
as $$
begin 
	return (select * from shoulder_get_start(degree::real, g - b, (g - b/2)::real, g, var));
end;
$$ language plpgsql;


create or replace function gauss_get_end(degree real, g real, b real, var int)
returns real 
as $$
begin 
	return (select * from shoulder_get_end((1 - degree)::real, g + b, (g + b/2)::real, g, var));
end
$$ language plpgsql;


select * from shoulder(60, 80, 65, 50);
select shoulder_get_start((1 - 0.7)::real, 50, 65, 80, 2);
select shoulder_get_start(0.678, 80.0, 65, 50, 2) as "start", shoulder_get_end(0.678, 80.0, 65, 50, 2) as "end"; 
select * from shoulder_get_end(1-0.144, 80, 70, 60, null); 
select * from gauss_get_start(0, 60.0, 20.0); 
select * from gauss(58, 60.0, 20.0); 


create or replace function linguistic_get_start(degree real, barrier linguistic_barrier, pid int)
returns real 
as $$
declare 
	res real; 
begin 
	select case barrier
		when 'Very'
			then degree ^ 0.5
		when 'More or less'
			then degree ^ 2
		when 'Plus'
			then degree ^ (4/5)
		when 'Not'
			then 1 - degree -- x = 1 - y^2
		when 'Not very'
			then (1 - degree) ^ 0.5
		end
	into res;
	return (
		select case m_type 
			when 'trapezoidal' then (select * from trapezoidal_get_start(res, parameter1, parameter2, parameter3, parameter4))
			when 'triangle' then (select * from triangle_get_start(res, parameter1, parameter2, parameter3))
			when 'shoulder' then (select * from shoulder_get_start(res, parameter1, parameter2, parameter3, v_id))
			when 'gauss' then (select * from gauss_get_start(res, parameter1, parameter2, v_id))
			when 'linguistic' then (select * from linguistic_get_start(res, barrier, p_id))
			end
		from membership_function 
		where m_id = pid);
end
$$ language plpgsql;

create or replace function linguistic_get_end(degree real, barrier linguistic_barrier, pid int)
returns real 
as $$
declare 
	res real; 
begin 
	select case barrier
		when 'Very'
			then degree ^ 0.5
		when 'More or less'
			then degree ^ 2
		when 'Plus'
			then degree ^ (4/5)
		when 'Not'
			then 1 - degree -- x = 1 - y^2
		when 'Not very'
			then (1 - degree) ^ 0.5
		end
	into res;
	return (
		select case m_type 
			when 'trapezoidal' then (select * from trapezoidal_get_end(res, parameter1, parameter2, parameter3, parameter4))
			when 'triangle' then (select * from triangle_get_end(res, parameter1, parameter2, parameter3))
			when 'shoulder' then (select * from shoulder_get_end(res, parameter1, parameter2, parameter3, v_id))
			when 'gauss' then (select * from gauss_get_end(res, parameter1, parameter2, v_id))
			when 'linguistic' then (select * from linguistic_get_end(res, barrier, p_id))
			end
		from membership_function 
		where m_id = pid);
end
$$ language plpgsql;

create or replace function memb_func_get_start(degree real, mf_id int)
returns real 
as $$
begin 
	return (
		select case m_type 
			when 'trapezoidal' then (select * from trapezoidal_get_start(degree, parameter1, parameter2, parameter3, parameter4))
			when 'triangle' then (select * from triangle_get_start(degree, parameter1, parameter2, parameter3))
			when 'shoulder' then (select * from shoulder_get_start(degree, parameter1, parameter2, parameter3, v_id))
			when 'gauss' then (select * from gauss_get_start(degree, parameter1, parameter2, v_id))
			when 'linguistic' then (select * from linguistic_get_start(degree, barrier, p_id))
			end
		from membership_function mf
		where m_id = mf_id);
end
$$ language plpgsql;

create or replace function memb_func_get_end(degree real, mf_id int)
returns real 
as $$
begin 
	return (
		select case m_type 
			when 'trapezoidal' then (select * from trapezoidal_get_end(degree, parameter1, parameter2, parameter3, parameter4))
			when 'triangle' then (select * from triangle_get_end(degree, parameter1, parameter2, parameter3))
			when 'shoulder' then (select * from shoulder_get_end(degree, parameter1, parameter2, parameter3, v_id))
			when 'gauss' then (select * from gauss_get_end(degree, parameter1, parameter2, v_id))
			when 'linguistic' then (select * from linguistic_get_end(degree, barrier, p_id))
			end
		from membership_function mf
		where m_id = mf_id);
end
$$ language plpgsql;


