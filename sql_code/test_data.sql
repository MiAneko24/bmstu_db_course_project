

insert into "system" values
(3, 'Laboratory work', 'Sugeno', 'informatics');

delete from variable where v_name = 's2';
insert into variable values
(10, 'a1', 0, 100, null, 3),
(11, 'a2', 100, 200, null, 3),
(12, 's1', 0, 500, null, 3);

insert into membership_function values
(20, 'a11', 'triangle', 10, 10, 30, 60),
(21, 'a12', 'triangle', 10, 30, 60, 80),
(22, 'a21', 'triangle', 11, 110, 120, 170),
(23, 'a22', 'triangle', 11, 120, 170, 180),
(24, null, 'linear', 10, 0.04315, null, null),
(25, null, 'linear', 11, -0.3974, null, null),
(26, null, 'crisp', null, 307.567, null, null),
(27, null, 'linear', 10, 0.25253, null, null),
(28, null, 'linear', 11, -0.16091, null, null),
(29, null, 'crisp', null, 303.608, null, null);

update membership_function 
set parameter1 = 0.16091
where m_id = 28;

insert into "rule" values
(10, 3, 'and', 1),
(11, 3, 'and', 1);

insert into antecedent 
values
(10, 20),
(11, 22),
(12, 21),
(13, 23);

insert into rule_antecedents 
values
(10, 10),
(10, 11),
(11, 12),
(11, 13);
--
--update consequent 
--set v_name = 's1'
--where v_name = 's2';

insert into consequent values
(10, 24, 10, 12),
(11, 25, 10, 12),
(12, 26, 10, 12),
(13, 27, 11, 12),
(14, 28, 11, 12),
(15, 29, 11, 12);

update variable 
set v_value = 55
where v_name ='a1';

update variable 
set v_value = 130
where v_name = 'a2';

select * from get_result_Sugeno(3);

------------------------------


insert into "system" 
values
(4, 'Accumulators', 'Sugeno', 'physics');

insert into variable values
(20, 'in1', -62.652, -12.365187, null, 4),
(21, 'in2', 1.95734, 99.9969, null, 4),
(22, 'out1', 2.5081366, 3.527538, null, 4);


insert into membership_function values
(30, 'in1cluster1', 'gauss', 20, -12.538318, 16.0191136),
(31, 'in1cluster2', 'gauss', 20, -12.693450, 16.64626),
(32, 'in1cluster3', 'gauss', 20, -25.042453, 16.01828),
(33, 'in1cluster4', 'gauss', 20, -62.62041, 16.007),
(34, 'in1cluster5', 'gauss', 20, -24.92186, 15.328027),
(35, 'in2cluster1', 'gauss', 21, 63.77841, 13.96134),
(36, 'in2cluster2', 'gauss', 21, 27.59403, 12.759025),
(37, 'in2cluster3', 'gauss', 21, 91.245513, 13.889254),
(38, 'in2cluster4', 'gauss', 21, 57.84007, 14.074748),
(39, 'in2cluster5', 'gauss', 21, 16.981307, 14.818675),
(40, 'out1cluster1', 'linear', 20, 0.00249216, null),
(41, 'out1cluster1', 'linear', 21, 0.0035852096, null),
(42, 'out1cluster1', 'crisp', null, 3.05265048, null),
(43, 'out1cluster2', 'linear', 20, 0.060665, null),
(44, 'out1cluster2', 'linear', 21, 0.002954407, null),
(45, 'out1cluster2', 'crisp', null, 2.9345918, null),
(46, 'out1cluster3', 'linear', 20, 0.0018589, null),
(47, 'out1cluster3', 'linear', 21, 0.005227, null),
(48, 'out1cluster3', 'crisp', null, 2.8266412, null),
(49, 'out1cluster4', 'linear', 20, 0.00205711, null),
(50, 'out1cluster4', 'linear', 21, 0.004130177, null),
(51, 'out1cluster4', 'crisp', null, 2.97927264, null),
(52, 'out1cluster5', 'linear', 20, 0.0192987, null),
(53, 'out1cluster5', 'linear', 21, 0.08263298, null),
(54, 'out1cluster5', 'crisp', null, 2.85392466, null);

insert into "rule" values
(20, 4, 'and', 1),
(21, 4, 'and', 1),
(22, 4, 'and', 1),
(23, 4, 'and', 1),
(24, 4, 'and', 1);


insert into antecedent 
values
(20, 30),
(21, 31),
(22, 32),
(23, 33),
(24, 34),
(25, 35),
(26, 36),
(27, 37),
(28, 38),
(29, 39);

insert into rule_antecedents 
values
(20, 20),
(20, 25),
(21, 21),
(21, 26),
(22, 22),
(22, 27),
(23, 23),
(23, 28),
(24, 24),
(24, 29);




insert into consequent values
(20, 40, 20, 22),
(21, 41, 20, 22),
(22, 42, 20, 22),
(23, 43, 21, 22),
(24, 44, 21, 22),
(25, 45, 21, 22),
(26, 46, 22, 22),
(27, 47, 22, 22),
(28, 48, 22, 22),
(29, 49, 23, 22),
(30, 50, 23, 22),
(31, 51, 23, 22),
(32, 52, 24, 22),
(33, 53, 24, 22),
(34, 54, 24, 22);


update variable 
set v_value = -50.33632
where v_name ='in1';

update variable 
set v_value = 67.382009
where v_name = 'in2';
select * from get_result_Sugeno(4);



------------------------------------------------------

insert into "system" values
(5, 'Accidents', 'Mamdani', 'informatics');

insert into variable values
(30, 'Age', 0, 100, null, 5),
(31, 'Accident probability', 0, 1, null, 5);

insert into "rule" values
(30, 5, 'and', 1),
(31, 5, 'and', 1);

insert into membership_function values
(60, 'Young', 'shoulder', 30, 36, 29, 22),
(61, 'Middle', 'gauss', 30, 40, 24, null),
(62, 'Very old', 'shoulder', 30, 51, 64, 77),
(63, 'Low', 'shoulder', 31, 0.8, 0.4, 0),
(64, 'High', 'shoulder', 31, 0.2, 0.6, 1);

insert into antecedent values
(30, 60),
(31, 61);

insert into rule_antecedents values
(30, 30),
(31, 31);
 

insert into consequent values
(40, 63, 31),
(41, 64, 30);

update variable set v_value = 28 where v_name ='Age';

select * from get_output(5);


