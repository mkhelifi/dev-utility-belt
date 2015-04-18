create table log_record (
  id       char(32)     not null,
  filename varchar(50)  not null,
  instant  timestamp,
  level    varchar(20),
  name     varchar(100),
  thread   varchar(100),
  message  text
);

alter table log_record add constraint pk_log_record primary key (id);
