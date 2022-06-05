--
--create or replace function get_output(sys_id int) 
--returns table (
--	v_name text,
--	v_value real
--)
--as $$
--declare 
--	tmp table (
--		r_id int,
--		w real
--	);
--	t system_type;
--begin
--	select 
--	from system s 
--		join rule r on s.s_id = r.s_id 
--		join rule_antecedents ra on r.r_id = ra.r_id 
--		join antecedent a on ra.a_id  = a.a_id 
--		join membership_function mf on mf.m_id = a.m_id 
--	where s.s_id = sys_id
--	group by r.r_id
--	into tmp;
--
--	select s."type" 
--	from "system" s 
--	where s.s_id = sys_id
--	into t;
--
--	select (case )
--	from 
--	
--end
--$$ language plpgsql;

select * from "system" s;
insert into "system" values
(2, 'Контроль заряда LI-ion батареек', 'Sugeno', 'physics');

insert into "rule" values
(1, 1, 'or', 0.2);
insert into "rule"  values
(2, 1, 'and');
insert into "rule" values
(3,1, 'and', 0.9);


delete from rule_antecedents 
where a_id = 2;

update variable 
set v_value = 63
where v_name = 'Pressure';

insert into "rule" values
(4, 2, 'and', 0.2),
(5, 2, 'or', 0.92);


insert into variable values
(3, 'Volume', 15, 90, null, 1);

insert into membership_function values
(14, 'Cons', 'linear', 1, 0.72, null, null),
(15, 'High', 'shoulder', 2, 50, 65, 80),
(16, 'Cons', 'linear', 2, 0.55, null, null),
(17, 'Cons', 'crisp', null, 0.22, null, null);


insert into antecedent 
values
(1, 2),
(2, 3),
(3, 5),
(4, 7),
(5, 10),
(6, 9),
(7, 15);

select * from "rule" r ;

insert into rule_antecedents values
(1, 2),
(1, 4),
(1, 6),
(2, 1),
(2, 2),
(2, 5),
(2, 6),
(3, 2),
(3, 3),
(3, 5);

update membership_function
set parameter1 = 12
where m_id = 2;

update antecedent 
set m_id = 11
where a_id = 6;

insert into rule_antecedents values
(4, 6),
(5, 1),
(5, 5),
(5, 7);


select * from "rule" r where r.s_id =2;	

insert into consequent values
(1, 12, 3, 2),
(2, 17, 3, 2),
(3, 14, 4, 2),
(4, 14, 5, 3),
(5, 13, 5, 3),
(6, 16, 5, 3);

--select count(*) from system2 ;


--select mf1.var_name , mf1.value
--						from rule_antecedents ra1
--							join antecedent a1 on ra1.antecedent_id  = a1.id 
--							join membership_function mf1 on mf1.id = a1.membership_function_id
--						where ra1.rule_id = 5;
--					group by mf1.var_name ;

drop function count_antecedents;
					
create or replace function count_antecedents(sys_id int)
returns table (
	r_id int,
	ant_value real
)
as $$
begin
	return query (select distinct r.r_id as "rule_id", case 
				when exists(select * from rule_antecedents ra1
								join antecedent a1 on ra1.a_id  = a1.a_id 
								join membership_function mf1 on mf1.m_id = a1.m_id
							where ra1.r_id = r.r_id and not mf1.is_active)
							then 0.0
				when r.antecedent_connection = 'or' then (select max(mf1.m_value) * r.weight
							from rule_antecedents ra1
								join antecedent a1 on ra1.a_id  = a1.a_id 
								join membership_function mf1 on mf1.m_id = a1.m_id
							where ra1.r_id = r.r_id) 
				when r.antecedent_connection = 'and' then (select min(mf1.m_value) * r.weight
							from rule_antecedents ra1
								join antecedent a1 on ra1.a_id  = a1.a_id 
								join membership_function mf1 on mf1.m_id = a1.m_id 
								where ra1.r_id = r.r_id)
				end as "value"
	from system s 
			join rule r on s.s_id = r.s_id 
			join rule_antecedents ra on r.r_id = ra.r_id 
			join antecedent a on ra.a_id  = a.a_id 
			join membership_function mf on mf.m_id = a.m_id
		where s.s_id = sys_id);
end;
$$ language plpgsql;


	
select r.r_id as rule, a.a_id as ant, mf.m_id as mf, mf.m_value
from rule r  
		join rule_antecedents ra on r.r_id = ra.r_id 
		join antecedent a on ra.a_id  = a.a_id 
		join membership_function mf on mf.m_id = a.m_id ;

select * from variable v ;

create or replace function get_output_Sugeno(s_id int)
returns table (
	r_id int,
	consequent int,
	value real,
	weight real
)
as $$
begin
	return query 
		(select q.r_id, q.consequent, sum(q.m_value) as "value", q.weight::real as "weight"
		from 
			(select distinct a.r_id, c.v_id as "consequent", mf.m_value, a.ant_value as "weight"
			from (select * from count_antecedents(s_id)) a
				join consequent c on a.r_id = c.r_id
				join membership_function mf on c.m_id = mf.m_id  
			where a.ant_value != 0 and mf.is_active) q
		group by q.r_id, q.consequent, q.weight);
end
$$ language plpgsql;


select * from count_antecedents(2); 
select * from get_result_sugeno(2);

insert into membership_function values
(18, 'Normal', 'gauss', 3, 60, 20, null),
(19, 'Hight', 'shoulder', 3, 68, 74, 80);


insert into consequent values
(7, 18, 1),
(8, 19, 2);

update variable 
set v_value = 55
where v_name = 'Volume';



select * from get_output(2);

create or replace function get_result_Sugeno(s_id int)
returns table (
	v_id int,
	value real
)
as $$
begin
	return query 
	(select q.consequent, sum(q.weight * q.value) / sum(q.weight) as "value"							
		from get_output_Sugeno(s_id) q
		group by q.consequent
		);
end
$$ language plpgsql;

select * from get_result_Sugeno(2);


--create or replace function get_mf_center_max(m_id int)
--returns real 
--as $$
--declare 
--	res real;
--begin
--	select case "type" 
--		when 'triangle' then parameter2 
--		when 'trapezoidal' then (parameter2 + parameter3) / 2
--		when 'shoulder' then parameter3
--		when 'gauss' then parameter1 
--		when 'linguistic' then (select * from get_mf_center_max(p_id))
--		end
--	from membership_function mf
--	where mf.m_id = m_id
--	into res;
--	return res;
--end;
--$$ language plpgsql;

--select get_mf_center_max(mf.m_id) from membership_function mf 

drop function get_fuzzy_Mamdani_output;

create or replace function get_fuzzy_Mamdani_output(s_id int)
returns table (
	v_id int,
	value real,
	cent real
)
as $$
begin
	return query 
	(
	with intervals as (
	select q.r_id, q.v_id, q.m_id, q.value, 
							memb_func_get_start(q.value, q.m_id) as "start", 
							memb_func_get_end(q.value, q.m_id) as "end"
	from 
		(select c.r_id, mf.v_id, mf.m_id, case r.antecedent_connection 
							when 'or' then case
										when max(ant_value) > max(mf.m_value) or mf.m_value is null
											then --'max ant'
												max(ant_value)
										else --'max mf'
											max(mf.m_value)
										end
							else case 
								when min(ant_value) < min(mf.m_value) or mf.m_value is null
									then --'min ant'
										min(ant_value)
								else --'min mf'
									min(mf.m_value)
								end
							end as "value"
			from count_antecedents(s_id) a
			join "rule" r  on a.r_id = r.r_id
			join consequent c on c.r_id = r.r_id 
			join membership_function mf on c.m_id = mf.m_id
			where mf.is_active 
			group by c.r_id, mf.v_id, mf.m_id, r.antecedent_connection) q
	)
	select q1.v_id, q1.value, ((q1.start + q1.end) / 2)::real as "cent" 
	from
		(select q.v_id, q.value, case
						when (select max("end") 
								from intervals i 
								where i.value > q.value and i.end < q.end) > q.start
								then (select max("end") 
								from intervals i 
								where i.value > q.value and i.end < q.end) 
						else q.start
						end as "start",
						case
						when (select min("start") 
								from intervals i 
								where i.value > q.value and i.start > q.start) < q.end
								then (select min("start") 
								from intervals i 
								where i.value > q.value and i.start > q.start) 
						else q.end
						end as "end"
							--max(q.value) as "value"
		from intervals q) q1);
end;
$$ language plpgsql;


select * from get_fuzzy_Mamdani_output(1);

create or replace function get_result_Mamdani(s_id int)
returns table (
	v_id int,
	value real
)
as $$
begin
	return query 
	(select q.v_id, case sum(q.value) 
					when 0 then 0
					else sum(q.value * q.cent) / sum(q.value)
					end as "value"							
		from get_fuzzy_Mamdani_output(s_id) q
		group by q.v_id
		);
end
$$ language plpgsql;

select * from get_result_Mamdani(1);

	
select t.value from get_result_Mamdani(1) t join variable v on t.v_id = v.v_id;
select * from count_antecedents(306863171);
select m_id, term, mf.v_id, mf.m_value, mf.m_type, mf.parameter1, mf.parameter2 from membership_function mf 
join variable v on mf.v_id = v.v_id 
where v.s_id = 200750254;

select * from get_output(200750254);
select * from get_fuzzy_mamdani_output(200750254); 

create or replace function get_output(sys_id int)
returns table(
	var_name text,
	value real
)
security definer
as $$
declare
	t text;
begin
	drop table if exists tmp;
	create temp table tmp
	(
		v_id int,
		value real
	);
	select s_type 
	from system s
	where s.s_id = sys_id 
	into t;
	if (t = 'Sugeno') then
		--return query (
		insert into tmp select * from get_result_Sugeno(sys_id);-- into tmp;
	else
--		return query (
		insert into tmp select * from get_result_Mamdani(sys_id);-- into tmp;
	end if;
--	update variable set v_value = (select t.value from tmp t where t.v_id = v_id) 
--	where v_id in (select t.v_id from tmp t) and s_id = sys_id;
	return query (select v_name, t.value 
			from tmp t join variable v on t.v_id = v.v_id);
end;
$$ language plpgsql;

select * from get_output(2);
