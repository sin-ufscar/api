--Criação do schema autenticacao
CREATE SCHEMA autenticacao;

--Alterar dono para postgres
ALTER SCHEMA autenticacao OWNER TO postgres;

--Criar tabela autenticacao.usuario
CREATE TABLE autenticacao.usuario
(
  id bigserial NOT NULL,
  version bigint NOT NULL,
  password character varying(255) NOT NULL,
  username character varying(255) NOT NULL,
  CONSTRAINT usuario_pkey PRIMARY KEY (id),
  CONSTRAINT uk_usuario_username UNIQUE (username)
)
WITH (
  OIDS=FALSE
);

--Alterar dono da tabela autenticacao.usuario para postgres
ALTER TABLE autenticacao.usuario OWNER TO postgres;

--Dar permissões para usuário postgres
GRANT ALL ON TABLE autenticacao.usuario TO postgres;