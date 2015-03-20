create table log_record (
  id       char(32)     not null,
  filename varchar(50)  not null,
  instant  timestamp        null,
  level    varchar(20)      null,
  name     varchar(100)     null,
  thread   varchar(100)     null,
  message  text             null
) engine innodb;

alter table log_record add constraint pk_log_record primary key (id);
