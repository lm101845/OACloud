/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2023/9/6 17:05:00                            */
/*==============================================================*/
drop database if exists oadb;
create database oadb CHARACTER SET utf8 COLLATE utf8_general_ci;
use oadb;

drop table if exists t_dep;

drop table if exists t_emp;

drop table if exists t_fun;

drop table if exists t_leave;

drop table if exists t_mgr;

drop table if exists t_rf;

drop table if exists t_role;

drop table if exists t_task;

drop table if exists t_task_rev;

drop table if exists t_ur;

drop table if exists t_user;

/*==============================================================*/
/* Table: t_dep                                                 */
/*==============================================================*/
create table t_dep
(
   d_id                 int not null auto_increment,
   d_name               varchar(50) not null,
   d_remark             varchar(200),
   d_status             int not null,
   primary key (d_id)
);

/*==============================================================*/
/* Table: t_emp                                                 */
/*==============================================================*/
create table t_emp
(
   e_id                 varchar(10) not null,
   d_id                 int,
   e_name               varchar(50) not null,
   e_sex                int not null,
   e_birth              date,
   e_status             int not null,
   primary key (e_id)
);

/*==============================================================*/
/* Table: t_fun                                                 */
/*==============================================================*/
create table t_fun
(
   f_id                 int not null,
   f_pid                int,
   f_name               varchar(50) not null,
   f_url                varchar(100),
   f_ismenu             int not null,
   primary key (f_id)
);

/*==============================================================*/
/* Table: t_leave                                               */
/*==============================================================*/
create table t_leave
(
   l_id                 bigint not null auto_increment,
   e_id                 varchar(10),
   l_cause              varchar(200) not null,
   l_start              datetime not null,
   l_end                datetime not null,
   l_submit_date        datetime,
   l_sp_advice          varchar(200),
   l_spr                varchar(50),
   l_sp_date            datetime,
   l_back_date          datetime,
   l_status             int not null,
   primary key (l_id)
);

/*==============================================================*/
/* Table: t_mgr                                                 */
/*==============================================================*/
create table t_mgr
(
   dep_id               int not null,
   leader_id            varchar(10) not null,
   primary key (dep_id)
);

/*==============================================================*/
/* Table: t_rf                                                  */
/*==============================================================*/
create table t_rf
(
   ro_id                int not null,
   f_id                 int not null,
   primary key (ro_id, f_id)
);

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   ro_id                int not null,
   ro_name              varchar(50) not null,
   primary key (ro_id)
);

/*==============================================================*/
/* Table: t_task                                                */
/*==============================================================*/
create table t_task
(
   task_id              bigint not null auto_increment,
   e_id                 varchar(10),
   task_title           varchar(100) not null,
   task_details         varchar(1000) not null,
   task_status          int,
   primary key (task_id)
);

/*==============================================================*/
/* Table: t_task_rev                                            */
/*==============================================================*/
create table t_task_rev
(
   tr_id                bigint not null auto_increment,
   task_id              bigint,
   e_id                 varchar(10),
   tr_assi_date         datetime not null,
   tr_rev_date          datetime,
   tr_report            varchar(1000),
   tr_report_date       datetime,
   tr_check_advice      varchar(1000),
   tr_check_date        datetime,
   tr_status            int,
   primary key (tr_id)
);

/*==============================================================*/
/* Table: t_ur                                                  */
/*==============================================================*/
create table t_ur
(
   u_id                 varchar(10) not null,
   ro_id                int not null,
   primary key (u_id, ro_id)
);

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   u_id                 varchar(10) not null,
   u_name               varchar(50) not null,
   u_pwd                varchar(20) not null,
   primary key (u_id)
);

alter table t_emp add constraint FK_Relationship_1 foreign key (d_id)
      references t_dep (d_id) on delete restrict on update restrict;

alter table t_fun add constraint FK_Relationship_8 foreign key (f_pid)
      references t_fun (f_id) on delete restrict on update restrict;

alter table t_leave add constraint FK_Relationship_2 foreign key (e_id)
      references t_emp (e_id) on delete restrict on update restrict;

alter table t_mgr add constraint FK_Reference_6 foreign key (dep_id)
      references t_dep (d_id) on delete restrict on update restrict;

alter table t_rf add constraint FK_Relationship_10 foreign key (f_id)
      references t_fun (f_id) on delete restrict on update restrict;

alter table t_rf add constraint FK_Relationship_9 foreign key (ro_id)
      references t_role (ro_id) on delete restrict on update restrict;

alter table t_task add constraint FK_Relationship_3 foreign key (e_id)
      references t_emp (e_id) on delete restrict on update restrict;

alter table t_task_rev add constraint FK_Relationship_4 foreign key (task_id)
      references t_task (task_id) on delete restrict on update restrict;

alter table t_task_rev add constraint FK_Relationship_5 foreign key (e_id)
      references t_emp (e_id) on delete restrict on update restrict;

alter table t_ur add constraint FK_Relationship_6 foreign key (u_id)
      references t_user (u_id) on delete restrict on update restrict;

alter table t_ur add constraint FK_Relationship_7 foreign key (ro_id)
      references t_role (ro_id) on delete restrict on update restrict;

