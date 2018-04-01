create table quotes
(
  id     int           not null
    primary key,
  author varchar(30)   null,
  text   varchar(1000) null,
  score  float         null,
  constraint quotes_id_uindex
  unique (id)
)
  engine = InnoDB;


