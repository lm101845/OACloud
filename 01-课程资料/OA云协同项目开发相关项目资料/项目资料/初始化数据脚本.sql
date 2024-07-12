delete from t_rf;
delete from t_ur;
delete from t_role;
delete from t_fun where f_pid is not null;
delete from t_fun;
delete from t_user;
delete from t_emp;
delete from t_dep;
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(1000,null,'安全管理',null,1);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(1005,1000,'登录','/security/login',0);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(1010,1000,'主页面','/security/home',0);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(1015,1000,'用户管理','/security/user',1);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(1020,1000,'角色管理','/security/role',1);

insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(2000,null,'基础信息',null,1);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(2005,2000,'部门信息','/info/dep',1);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(2010,2000,'员工信息','/info/emp',1);

insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(3000,null,'请假',null,1);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(3005,3000,'请假申请和销假','/leave/apprev',1);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(3010,3000,'请假审批','/leave/exam',1);

insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(4000,null,'任务',null,1);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(4005,4000,'任务新建和下达','/task/newassi',1);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(4010,4000,'任务接收和汇报','/task/recrep',1);
insert into t_fun(f_id,f_pid,f_name,f_url,f_ismenu) values(4015,4000,'任务验收','/task/check',1);


insert into t_role(ro_id,ro_name) values(1,'系统管理员'),(2,'信息管理员'),(3,'员工'),(4,'部门主管');

insert into t_rf(ro_id,f_id) values (1,1015),(1,1020),(2,2005),(2,2010),(3,3005),(3,4010),(4,3010),(4,4005),(4,4015);

insert into t_user(u_id,u_name,u_pwd) values('admin','用户1','123456'),('info','用户2','123456'),('emp','用户3','123456'),('leader','用户4','123456');

insert into t_ur(u_id,ro_id) values('admin',1),('info',2),('emp',3),('leader',4);


insert into t_dep(d_id,d_name,d_status) values (1,'人力资源部',2),(2,'财务部',2),(3,'设备部',2),(4,'研发部',2),(5,'市场部',2);

drop procedure if exists proc_emp_init;
delimiter $$
create procedure proc_emp_init
(cnt int)
begin    
	delete from t_emp;
    set @i = 1;
    while @i <= cnt do	
		insert into t_emp(e_id,e_name,e_sex,e_birth,d_id,e_status) values(lpad(@i,5,'0'),concat('员工_',round(rand()*99999)),floor(rand()*2+1),subdate(now(),INTERVAL (rand()*30+20)*365 day),floor(rand()*5+1),floor(rand()*3));
        set @i = @i+1;
	end while;
end$$
delimiter ;
call proc_emp_init(288);