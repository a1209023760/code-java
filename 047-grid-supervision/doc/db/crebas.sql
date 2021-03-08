/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/3/8 15:05:37                            */
/*==============================================================*/


drop table if exists task_task;

drop table if exists task_task_detail;

drop table if exists task_task_file;

drop table if exists task_task_grid;

drop table if exists task_task_grid_account;

drop table if exists task_task_handle_record;

drop table if exists task_task_handle_record_file;

drop table if exists task_theme;

drop table if exists task_theme_field;

drop table if exists task_theme_field_value;

/*==============================================================*/
/* Table: task_task                                             */
/*==============================================================*/
create table task_task
(
   id                   bigint not null auto_increment comment 'id',
   name                 varchar(32) comment '��������',
   type                 tinyint comment '��������[1 �ճ�Ѳ�죬2 ר��Ѳ�飬3 ��������]',
   priority             tinyint comment '�������ȼ�[1 ��ͨ��2 �ϵͣ�3 ������4 �ǳ�����]',
   begin_date           date comment '�������޿�ʼ����',
   end_date             date comment '�������޽�������',
   interval_type        varchar(32) comment '������[1 ÿ�գ�2 ÿ�ܣ�3 ÿ�£�4 ��]',
   frequency            int comment '����Ƶ��',
   note                 varchar(256) comment '����˵��',
   registrant           bigint comment '����Ǽ���',
   status               tinyint comment '��ǰ״̬[1 ������2 �����У�3 δ��ʼ��4 �ѹ鵵��5 ��ʧЧ]',
   processing_level     tinyint comment '������ȼ�[1 ����2 ��]',
   control_level        tinyint comment '������Ƶȼ�[1 ����2 ��]',
   supervisor           bigint comment '������',
   reminder             bigint comment '�߰���',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table task_task comment '�����';

/*==============================================================*/
/* Table: task_task_detail                                      */
/*==============================================================*/
create table task_task_detail
(
   id                   bigint not null auto_increment comment 'id',
   task_id              bigint comment '����id',
   begin_date           date comment '��������ʼ����',
   end_date             date comment '��������������',
   frequency            int comment '����Ƶ��',
   primary key (id)
);

alter table task_task_detail comment '������ϸ��';

/*==============================================================*/
/* Table: task_task_file                                        */
/*==============================================================*/
create table task_task_file
(
   id                   bigint not null comment 'id',
   task_id              bigint comment '�����id',
   file_name            varchar(128) comment '�ļ�����',
   primary key (id)
);

alter table task_task_file comment '�����ļ���';

/*==============================================================*/
/* Table: task_task_grid                                        */
/*==============================================================*/
create table task_task_grid
(
   id                   bigint not null comment 'id',
   task_id              bigint comment '����id',
   grid_id              bigint comment '����id',
   primary key (id)
);

alter table task_task_grid comment '���������';

/*==============================================================*/
/* Table: task_task_grid_account                                */
/*==============================================================*/
create table task_task_grid_account
(
   id                   bigint not null comment 'id',
   task_grid_id         bigint comment '��������id',
   account_id           bigint comment '��Աid',
   status               varchar(32) comment '��ǰ״̬[1 ������2 �����У�3 δ��ʼ��4 �ѹ鵵��5 ��ʧЧ]',
   processing_level     varchar(32) comment '������ȼ�[1 ����2 ��]',
   control_level        varchar(32) comment '������Ƶȼ�[1 ����2 ��]',
   score                int comment ' ����',
   primary key (id)
);

alter table task_task_grid_account comment '����������Ա��';

/*==============================================================*/
/* Table: task_task_handle_record                               */
/*==============================================================*/
create table task_task_handle_record
(
   id                   bigint not null auto_increment comment 'id',
   task_detail_id       bigint comment '������ϸid',
   note                 varchar(256) comment '����˵��',
   handle_id            bigint comment '���������',
   lon                  decimal(9,6) comment '����',
   lat                  decimal(8,6) comment 'γ��',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��',
   primary key (id)
);

alter table task_task_handle_record comment '��������¼��';

/*==============================================================*/
/* Table: task_task_handle_record_file                          */
/*==============================================================*/
create table task_task_handle_record_file
(
   id                   bigint not null comment 'id',
   file_name            varchar(128) comment '�ļ�����',
   task_handle_record_id bigint comment '��������¼id',
   primary key (id)
);

alter table task_task_handle_record_file comment '��������¼�ļ���';

/*==============================================================*/
/* Table: task_theme                                            */
/*==============================================================*/
create table task_theme
(
   id                   bigint not null comment 'id',
   name                 varchar(32) comment '��������',
   required             tinyint comment '�Ƿ�����ϴ�����[0 ��1 ����]',
   note                 varchar(256) comment '����˵��',
   create_time          datetime comment '����ʱ��',
   update_time          datetime comment '�޸�ʱ��',
   task_id              bigint comment '����id',
   primary key (id)
);

alter table task_theme comment '�����';

/*==============================================================*/
/* Table: task_theme_field                                      */
/*==============================================================*/
create table task_theme_field
(
   id                   bigint not null comment 'id',
   theme_id             bigint comment '����id',
   required             tinyint comment '�Ƿ����[0 ��1 ��]',
   field_name           varchar(32) comment '�ֶ�����',
   field_type           tinyint comment '�ֶ�����[1 �ı���2 ���ڣ�3 ѡ��]',
   field_type_detail    varchar(32) comment '�ֶ����Ͳ���[1 �����գ�2 ���£�3 ���գ���ѡ�����ֵ]',
   primary key (id)
);

alter table task_theme_field comment '�����ֶ�';

/*==============================================================*/
/* Table: task_theme_field_value                                */
/*==============================================================*/
create table task_theme_field_value
(
   id                   bigint not null comment 'id',
   field_value          varchar(32) comment '�ֶ�ֵ',
   theme_field_id       bigint comment '�����ֶ�id',
   task_handle_record_id bigint comment '��������¼id',
   primary key (id)
);

alter table task_theme_field_value comment '�����ֶ�ֵ';

alter table task_task_detail add constraint FK_Reference_11 foreign key (task_id)
      references task_task (id) on delete restrict on update restrict;

alter table task_task_file add constraint FK_Reference_8 foreign key (task_id)
      references task_task (id) on delete restrict on update restrict;

alter table task_task_grid add constraint FK_Reference_9 foreign key (task_id)
      references task_task (id) on delete restrict on update restrict;

alter table task_task_grid_account add constraint FK_Reference_10 foreign key (task_grid_id)
      references task_task_grid (id) on delete restrict on update restrict;

alter table task_task_handle_record add constraint FK_Reference_14 foreign key (task_detail_id)
      references task_task_detail (id) on delete restrict on update restrict;

alter table task_task_handle_record_file add constraint FK_Reference_15 foreign key (task_handle_record_id)
      references task_task_handle_record (id) on delete restrict on update restrict;

alter table task_theme add constraint FK_Reference_12 foreign key (task_id)
      references task_task (id) on delete restrict on update restrict;

alter table task_theme_field add constraint FK_Reference_13 foreign key (theme_id)
      references task_theme (id) on delete restrict on update restrict;

alter table task_theme_field_value add constraint FK_Reference_16 foreign key (theme_field_id)
      references task_theme_field (id) on delete restrict on update restrict;

alter table task_theme_field_value add constraint FK_Reference_17 foreign key (task_handle_record_id)
      references task_task_handle_record (id) on delete restrict on update restrict;

