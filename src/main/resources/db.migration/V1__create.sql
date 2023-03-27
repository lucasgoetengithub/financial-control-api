CREATE TABLE users
(
  id integer NOT NULL,
  amount numeric(19,2),
  email character varying(255),
  name character varying(255),
  password character varying(255),
  CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE perfis
(
  users_id integer NOT NULL,
  perfis integer,
  CONSTRAINT fkb2ds6qb1p6ecodn2dymnnnd5f FOREIGN KEY (users_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE distribution_where_invest
(
  id integer NOT NULL,
  json_distribution_where_invests json,
  where_invest_id integer,
  CONSTRAINT distribution_where_invest_pkey PRIMARY KEY (id)
);

CREATE TABLE where_invest
(
  id integer NOT NULL,
  json_where_invests json,
  userId integer,
  reference DATE,
  amount numeric(19,2),
  CONSTRAINT where_invest_pkey PRIMARY KEY (id)
);