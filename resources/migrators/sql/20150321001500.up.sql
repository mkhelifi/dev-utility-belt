create table log_record (
  id       text primary key not null,
  filename text             not null,
  instant  text,
  level    text,
  name     text,
  thread   text,
  message  text
);
