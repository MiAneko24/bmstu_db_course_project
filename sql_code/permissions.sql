
create role administrator superuser;
grant administrator to admin;
revoke all on all tables in schema public from public;
revoke all on database postgres from public;
revoke all on schema public from public;
grant connect on database postgres to user_role;

grant usage on schema pg_catalog to user_role;

create role user_role;
revoke all on schema public from user_role;
set search_path to public;

grant usage on schema public to user_role;
revoke all on all tables in schema public from user_role;
grant select on all tables in schema public to user_role;
revoke all on all functions in schema public from user_role;

grant execute on function get_output(int) to user_role;
create or replace procedure set_variable_value(var_id int, value real)
security definer
as $$
begin
	update variable set v_value = value where v_id = var_id;
end
$$ language plpgsql; 
grant execute on procedure set_variable_value(int, real) to user_role;

create or replace function get_roles() 
returns table(
	usr_role name)
as $$
begin
	return query 
		select rolname from pg_roles where pg_has_role((select current_user), oid, 'member');
end;
$$ language plpgsql; 

grant select on table pg_roles to user_role;

grant execute on function get_roles() to user_role;

create user usr inherit
login
password '123';

select * from "system" s ;

grant user_role to usr;

create role expert inherit;

grant select on all tables in schema pg_catalog to admin;
----------------PHYSICS EXPERTS VIEWS--------------------

drop view if exists physics_expert_rule_antecedents;
drop view if exists physics_expert_antecedents;
drop view if exists physics_expert_consequents;
drop view if exists physics_expert_membership_functions;
drop view if exists physics_expert_rules;
drop view if exists physics_expert_variables;
drop view if exists physics_expert_systems;

create view physics_expert_systems as 
	select * 
	from system 
	where specialization = 'physics' 
with cascaded check option;

create view physics_expert_variables as 
	select * 
	from variable 
	where s_id in (
		select s_id from physics_expert_systems)
with cascaded check option;

create view physics_expert_membership_functions as
	select * 
	from membership_function
	where v_id in (select v_id from physics_expert_variables)
with cascaded check option;

create view physics_expert_rules as
	select *
	from rule
	where s_id in (select s_id from physics_expert_systems)
with cascaded check option;

create view physics_expert_antecedents as
	select * from antecedent a 
	where m_id in (select m_id from physics_expert_membership_functions)
with cascaded check option;

create view physics_expert_consequents as
	select * from consequent c 
	where r_id in (select r_id from physics_expert_rules) 
		and m_id in (select m_id from physics_expert_membership_functions)
		and (v_id is null or v_id in (select v_id from physics_expert_variables))
with cascaded check option;

create view physics_expert_rule_antecedents as
	select * from rule_antecedents ra 
	where r_id in (select r_id from physics_expert_rules)
		and a_id in (select a_id from physics_expert_antecedents)
with cascaded check option;

-----------------------------------------------------

grant user_role to expert; 
create role physics inherit;
create role chemistry inherit;
create role informatics inherit;

grant expert to physics;
grant expert to chemistry;
grant expert to informatics;


grant all on physics_expert_variables to physics;
grant all on physics_expert_rule_antecedents to physics;
grant all on physics_expert_antecedents to physics;
grant all on physics_expert_consequents to physics;
grant all on physics_expert_membership_functions to physics;
grant all on physics_expert_rules to physics;
grant all on physics_expert_systems to physics;


----------------CHEMISTRY EXPERTS VIEWS--------------------
drop view if exists chemistry_expert_rule_antecedents;
drop view if exists chemistry_expert_antecedents;
drop view if exists chemistry_expert_consequents;
drop view if exists chemistry_expert_membership_functions;
drop view if exists chemistry_expert_rules;
drop view if exists chemistry_expert_variables;
drop view if exists chemistry_expert_systems;


create view chemistry_expert_systems as 
	select * 
	from system 
	where specialization = 'chemistry' 
with cascaded check option;

create view chemistry_expert_variables as 
	select * 
	from variable 
	where s_id in (
		select s_id from chemistry_expert_systems)
with cascaded check option;

create view chemistry_expert_membership_functions as
	select * 
	from membership_function
	where v_id in (select v_id from chemistry_expert_variables)
with cascaded check option;

create view chemistry_expert_rules as
	select *
	from rule
	where s_id in (select s_id from chemistry_expert_systems)
with cascaded check option;

create view chemistry_expert_antecedents as
	select * from antecedent a 
	where m_id in (select m_id from chemistry_expert_membership_functions)
with cascaded check option;

create view chemistry_expert_consequents as
	select * from consequent c 
	where r_id in (select r_id from chemistry_expert_rules) 
		and m_id in (select m_id from chemistry_expert_membership_functions)
		and (v_id is null or v_id in (select v_id from chemistry_expert_variables))
with cascaded check option;

create view chemistry_expert_rule_antecedents as
	select * from rule_antecedents ra 
	where r_id in (select r_id from chemistry_expert_rules)
		and a_id in (select a_id from chemistry_expert_antecedents)
with cascaded check option;

-----------------------------------------------------

grant all on chemistry_expert_variables to chemistry;
grant all on chemistry_expert_antecedents to chemistry;
grant all on chemistry_expert_rule_antecedents to chemistry;
grant all on chemistry_expert_consequents to chemistry;
grant all on chemistry_expert_membership_functions to chemistry;
grant all on chemistry_expert_rules to chemistry;
grant all on chemistry_expert_systems to chemistry;

----------------INFORMATICS EXPERTS VIEWS--------------------
drop view if exists informatics_expert_rule_antecedents;
drop view if exists informatics_expert_antecedents;
drop view if exists informatics_expert_consequents;
drop view if exists informatics_expert_membership_functions;
drop view if exists informatics_expert_rules;
drop view if exists informatics_expert_variables;
drop view if exists informatics_expert_systems;

create view informatics_expert_systems as 
	select * 
	from system 
	where specialization = 'informatics' 
with cascaded check option;

create view informatics_expert_variables as 
	select * 
	from variable 
	where s_id in (
		select s_id from informatics_expert_systems)
with cascaded check option;

create view informatics_expert_membership_functions as
	select * 
	from membership_function
	where v_id in (select v_id from informatics_expert_variables)
with cascaded check option;

create view informatics_expert_rules as
	select *
	from rule
	where s_id in (select s_id from informatics_expert_systems)
with cascaded check option;

create view informatics_expert_antecedents as
	select * from antecedent a 
	where m_id in (select m_id from informatics_expert_membership_functions)
with cascaded check option;

create view informatics_expert_consequents as
	select * from consequent c 
	where r_id in (select r_id from informatics_expert_rules) 
		and m_id in (select m_id from informatics_expert_membership_functions)
		and (v_id is null or v_id in (select v_id from informatics_expert_variables))
with cascaded check option;

create view informatics_expert_rule_antecedents as
	select * from rule_antecedents ra 
	where r_id in (select r_id from informatics_expert_rules)
		and a_id in (select a_id from informatics_expert_antecedents)
with cascaded check option;

------------------------------------------------------


grant all on informatics_expert_variables to informatics;
grant all on informatics_expert_antecedents to informatics;
grant all on informatics_expert_rule_antecedents to informatics;
grant all on informatics_expert_consequents to informatics;
grant all on informatics_expert_membership_functions to informatics;
grant all on informatics_expert_rules to informatics;
grant all on informatics_expert_systems to informatics;



create user physic inherit
login
password '892';
grant physics to physic;

create user chemist inherit
login
password '9222';
grant chemistry to chemist;

create user programmer inherit
login
password 'hfdh';
grant informatics to programmer; 
