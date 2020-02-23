/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/2/23 17:12:48                           */
/*==============================================================*/


drop table if exists t_uc_account;

drop table if exists t_uc_account_role;

drop table if exists t_uc_application;

drop table if exists t_uc_dict;

drop table if exists t_uc_menu;

drop table if exists t_uc_organization;

drop table if exists t_uc_role;

drop table if exists t_uc_role_app_menu;

drop table if exists t_uc_tenant;

/*==============================================================*/
/* Table: t_uc_account                                          */
/*==============================================================*/
create table t_uc_account
(
   id                   bigint unsigned not null auto_increment comment '����',
   account              varchar(32) comment '�û��˺�',
   password             varchar(32) comment '�û�����',
   account_name         varchar(32) comment '�û�����',
   user_type            char(14) comment '�û�����(SUPER_MANAGER ��������Ա��TENANT_MANAGER �⻧����Ա��COMMON_USER ��ͨ�û�)',
   phone                char(12) comment '�û��ֻ���',
   email                varchar(32) comment '�û�����',
   photo_url            varchar(1024) comment '�û�ͷ��url',
   ui_theme             varchar(32) comment '�û���������',
   org_id               bigint unsigned comment '��֯����id',
   tenant_id            bigint unsigned comment '�⻧id',
   active_flag          char(1) comment '����/����״̬(Y ���ã�N ����)',
   update_password_flag char(1) comment 'ǿ���޸�����״̬(Y ǿ���޸ģ�N �����޸�)',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��',
   primary key (id),
   unique key AK_Key_2 (account)
);

alter table t_uc_account comment '�û���Ϣ��';

/*==============================================================*/
/* Table: t_uc_account_role                                     */
/*==============================================================*/
create table t_uc_account_role
(
   id                   bigint unsigned not null auto_increment comment '����',
   account_id           bigint unsigned comment '�û�id',
   role_id              bigint unsigned comment '��ɫid',
   primary key (id),
   unique key AK_Key_2 (account_id, role_id)
);

alter table t_uc_account_role comment '�û���ɫ������';

/*==============================================================*/
/* Table: t_uc_application                                      */
/*==============================================================*/
create table t_uc_application
(
   id                   bigint unsigned not null auto_increment comment '����',
   full_name            varchar(32) comment 'Ӧ��ȫ��',
   simple_name          varchar(32) comment 'Ӧ�ü��',
   application_identifier varchar(32) comment 'Ӧ�ñ�ʶ��',
   logo_url             varchar(1024) comment 'LOGO URL',
   storage_type         char(9) comment '�洢��������(NOT_STORE ���洢��LIMITED �����ޣ�UNLIMITED ������)',
   storage_cycle        int unsigned comment '�洢����',
   storage_unit         char(5) comment '�洢���ڵ�λ(DAY �գ�MONTH �£�YEAR ��)',
   tenant_id            bigint unsigned comment '�⻧id',
   active_flag          char(1) comment '����/����״̬(Y ���ã�N ����)',
   delete_flag          char(1) comment '��Ч/ɾ��״̬(Y ��Ч��N ɾ��)',
   delete_time          datetime comment 'ɾ��ʱ��',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table t_uc_application comment 'Ӧ����Ϣ��';

/*==============================================================*/
/* Table: t_uc_dict                                             */
/*==============================================================*/
create table t_uc_dict
(
   id                   bigint unsigned not null auto_increment comment '����',
   dict_type            varchar(32) comment '�ֵ����',
   dict_key             varchar(32) comment '�ֵ��ֵ',
   dict_label           varchar(32) comment '�ֵ��ǩ',
   dict_sort            tinyint unsigned comment '�ֵ�����',
   remark               varchar(32) comment '��ע',
   css_class            varchar(32) comment '�ֵ���ʽ����',
   active_flag          char(1) comment '����/����״̬(Y ���ã�N ����)',
   primary key (id)
);

alter table t_uc_dict comment '�����ֵ��';

/*==============================================================*/
/* Table: t_uc_menu                                             */
/*==============================================================*/
create table t_uc_menu
(
   id                   bigint unsigned not null auto_increment comment '����',
   menu_name            varchar(32) comment '�˵�����',
   menu_type            char(1) comment '�˵�����(MĿ¼ C�˵� F��ť)',
   menu_url             varchar(1024) comment '�˵�url',
   menu_icon            varchar(32) comment '�˵�icon',
   parent_menu_id       bigint unsigned comment '�����˵�id',
   menu_sort            int unsigned comment '�˵�����',
   active_flag          char(1) comment '����/����״̬(Y ���ã�N ����)',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table t_uc_menu comment '�˵���Ϣ��';

/*==============================================================*/
/* Table: t_uc_organization                                     */
/*==============================================================*/
create table t_uc_organization
(
   id                   bigint unsigned not null auto_increment comment '����',
   org_name             varchar(32) comment '��֯��������',
   org_code             varchar(32) comment '��֯��������',
   parent_code          bigint unsigned comment 'Ӧ�ñ�ʶ��',
   tenant_id            bigint unsigned comment '�⻧id',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table t_uc_organization comment '��֯������Ϣ��';

/*==============================================================*/
/* Table: t_uc_role                                             */
/*==============================================================*/
create table t_uc_role
(
   id                   bigint unsigned not null auto_increment comment '����',
   role_name            varchar(32) comment '��ɫ����',
   role_remark          varchar(32) comment '��ɫ����',
   tenant_id            bigint unsigned comment '�⻧id',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table t_uc_role comment '��ɫ��Ϣ��';

/*==============================================================*/
/* Table: t_uc_role_app_menu                                    */
/*==============================================================*/
create table t_uc_role_app_menu
(
   id                   bigint not null auto_increment comment '����',
   app_id               bigint unsigned comment 'Ӧ��id',
   role_id              bigint unsigned comment '��ɫid',
   menu_id              bigint unsigned comment '�˵�id',
   primary key (id),
   unique key AK_Key_2 (app_id, role_id, menu_id)
);

alter table t_uc_role_app_menu comment '��ɫӦ�ò˵�������';

/*==============================================================*/
/* Table: t_uc_tenant                                           */
/*==============================================================*/
create table t_uc_tenant
(
   id                   bigint unsigned not null auto_increment comment '����',
   full_name            varchar(32) comment '�⻧ȫ��',
   simple_name          varchar(32) comment '�⻧���',
   tenant_identifier    varchar(32) comment '�⻧��ʶ��',
   logo_url             varchar(1024) comment 'LOGO URL',
   active_flag          char(1) comment '����/����״̬(Y ���ã�N ����)',
   delete_flag          char(1) comment '��Ч/ɾ��״̬(Y ��Ч��N ɾ��)',
   delete_time          datetime comment 'ɾ��ʱ��',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��',
   primary key (id),
   unique key AK_Key_2 (tenant_identifier)
);

alter table t_uc_tenant comment '�⻧��Ϣ��';

