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

create table usuario_permissao (
	usuario int,
	permissao varchar(255)
);

create table conta_bancaria (
	cod_conta int not null auto_increment,
	cod_usuario int not null,
	des_conta varchar(255) default null,
	dat_cadastro datetime not null,
	saldo_inicial float default null,
	favorita char(1) default null,
	primary key (cod_conta),
	key fk_conta_usuario (cod_usuario),
	constraint fk_conta_usuario
		foreign key (cod_usuario)
		references usuairo (codigo)
		on delete cascade
);