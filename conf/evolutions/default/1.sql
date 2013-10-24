# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table vente (
  id                        bigint not null,
  name                      varchar(255),
  date                      timestamp,
  constraint pk_vente primary key (id))
;

create sequence vente_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists vente;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists vente_seq;

