DELETE FROM leave_type;
DELETE FROM leave_record;
DELETE FROM employee;
DELETE FROM emp_leave;
DELETE FROM public_holiday;

INSERT INTO employee 
(emp_id, emp_name, pwd, role, designation, is_admin, manager_empid) 
VALUES ('emp','Chen Yingxuan','123','Employee','Engineer',false,'boss'),
	   ('boss','Tan Ah Teck','123','Manager','Principal Engineer',true,null);
       
INSERT INTO leave_type 
(leave_type, designation, default_num_of_days)
VALUES ('Annual','Engineer','18'),
	   ('Annual','Principal Engineer','21'),
       ('Medical','Engineer','60'),
       ('Medical','Principal Engineer','60');

INSERT INTO emp_leave 
(employee_empid, leave_type, leave_bal, leave_entitlement)
VALUES ('emp','Annual','18','18'),
	   ('emp','Medical','60','60'),
       ('boss','Annual','21','21'),
       ('boss','Medical','60','60');

INSERT INTO public_holiday 
(ph_name, ph_date)
VALUES ("New Year's Day",'2019-01-01'),
	   ('Chinese New Year','2019-02-05'),
       ('Chinese New Year','2019-02-06'),
       ('Good Friday','2019-04-19'),
       ('Labour Day','2019-05-01'),
       ('Vesak Day','2019-05-19'),
       ('Hari Raya Puasa','2019-06-05'),
       ('National Day','2019-08-09'),
       ('Hari Raya Haji','2019-08-11'),
       ('Deepavali','2019-10-27'),
       ('Christmas Day','2019-12-25');