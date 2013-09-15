drop database if exists financeiro;
create database financeiro;
use financeiro;

create table usuario (
	codigo int not null auto_increment,
	nome varchar(50) not null,
	login varchar(15) not null,
	email varchar(100) not null,
	senha varchar(32) not null,
	nascimento date not null,
	celular varchar(25),
	idioma varchar(5) not null,
	ativo tinyint(1) not null,
	primary key (codigo),
	unique key login (login)
);