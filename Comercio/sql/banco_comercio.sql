create database comercio;

use comercio;

create table categoria (
	cod_categoria int not null auto_increment,
	descricao varchar(50) null,
	primary key (cod_categoria)
);

create table produto (
	cod_produto int not null auto_increment,
	descricao varchar(50) null,
	preco decimal(8, 2) null,
	primary key (cod_produto)
);

create table categoria_produto (
	cod_categoria int not null,
	cod_produto int not null,
	primary key (cod_categoria, cod_produto),
	constraint fk_categoria_produto_categoria
		foreign key (cod_categoria)
		references categoria (cod_categoria)
		on delete no action
		on update no action,
	constraint fk_categoria_produto_produto
		foreign key (cod_produto)
		references produto (cod_produto)
		on delete no action
		on update no action
);