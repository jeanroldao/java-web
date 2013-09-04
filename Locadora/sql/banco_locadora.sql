drop database locadora;
create database locadora;
use locadora;

create table categoria (
	cod_categoria int not null auto_increment,
	descricao varchar(50) null,
	primary key (cod_categoria)
);

create table filme (
	cod_filme int not null auto_increment,
	cod_categoria int not null,
	descricao varchar(50) not null,
	ano date not null,
	primary key (cod_filme),
	constraint fk_filme_cod_categoria
		foreign key (cod_categoria)
		references categoria (cod_categoria)
		on delete no action
		on update cascade
);

create table cliente (
	cod_cliente int not null auto_increment,
	nome varchar(100) null,
	telefone varchar(50) null,
	celular varchar(50),
	email varchar(255) null,
	primary key (cod_cliente)
);

create table midia (
	cod_midia int not null auto_increment,
	cod_filme int not null,
	inutilizada char(1) null,
	primary key (cod_midia),
	constraint fk_midia_cod_filme
		foreign key (cod_filme)
		references filme (cod_filme)
		on delete no action
		on update cascade 
);

create table locacao (
	cod_locacao int not null auto_increment,
	cod_cliente int not null,
	cod_midia int not null,
	data_emprestimo date null,
	hora_emprestimo time null,
	data_devolucao date null,
	obs text null,
	primary key (cod_locacao, cod_cliente, cod_midia),
	constraint fk_locacao_cod_cliente
		foreign key (cod_cliente)
		references cliente (cod_cliente)
		on delete no action
		on update cascade,
	constraint fk_locacao_cod_midia
		foreign key (cod_midia)
		references midia (cod_midia)
		on delete no action
		on update cascade
);

create table endereco (
	cod_cliente int not null,
	rua varchar(100) null,
	numero int null,
	bairro varchar(100) null,
	cidade varchar(100) null,
	estado char(2) null,
	cep varchar(10) null,
	complemento text null,
	constraint fk_cliente_cod_cliente
		foreign key (cod_cliente)
		references cliente (cod_cliente)
		on delete no action
		on update cascade
);

