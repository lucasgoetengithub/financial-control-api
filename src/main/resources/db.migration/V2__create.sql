CREATE TABLE CALCULATOR_FIXA
(
  id integer NOT NULL,
  json_calculator_fixas json,
  user_id integer,
  meses integer,
  nome char(20),
  aporte_mensal numeric(19,2),
  porcentagem numeric(19,2),
  CONSTRAINT CALCULATOR_FIXA_pkey PRIMARY KEY (id)
);

CREATE TABLE CALCULATOR_VARIAVEL
(
  id integer NOT NULL,
  nome char(20),
  acoes_por_mes integer,
  dividendo_por_acao numeric(19,2),
  valor_da_acao numeric(19,2),
  user_id integer,
  json_calculator_variavel json,
  CONSTRAINT CALCULATOR_VARIAVEL_pkey PRIMARY KEY (id)
);