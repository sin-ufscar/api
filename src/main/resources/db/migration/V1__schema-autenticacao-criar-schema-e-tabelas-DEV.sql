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


--Criar tabela autenticacao.usuario
CREATE TABLE autenticacao.permissao
(
  id bigserial NOT NULL,
  version bigint NOT NULL,
  nome character varying(255) NOT NULL,
  descricao character varying(255) NOT NULL,
  CONSTRAINT permissao_pkey PRIMARY KEY (id),
  CONSTRAINT uk_permissao_nome UNIQUE (nome)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE autenticacao.permissao
  OWNER TO postgres;

--Dar permissões para usuário postgres
GRANT ALL ON TABLE autenticacao.permissao TO postgres;


CREATE TABLE autenticacao.usuario_permissao
(
  permissao_id bigint NOT NULL,
  usuario_id bigint NOT NULL,
  version bigint NOT NULL,
  id bigserial NOT NULL,
  CONSTRAINT uk_usuario_permissao_usuario_permissao UNIQUE (permissao_id, usuario_id),
  CONSTRAINT usuario_permissao_pkey PRIMARY KEY (id),
  CONSTRAINT fk_bmksi5jr2i4mv2x54kt5cmxjo FOREIGN KEY (permissao_id)
      REFERENCES autenticacao.permissao (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_kvamahk8ua44i0mfjkqmhqob2 FOREIGN KEY (usuario_id)
      REFERENCES autenticacao.usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION

)
WITH (
  OIDS=FALSE
);
ALTER TABLE autenticacao.usuario_permissao
  OWNER TO postgres;

--Dar permissões para usuário postgres
GRANT ALL ON TABLE autenticacao.usuario_permissao TO postgres