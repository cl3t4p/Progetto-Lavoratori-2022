

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';



CREATE TABLE public.comune (
    nome_comune character varying(60) NOT NULL
);



CREATE TABLE public.dipendente (
    id integer NOT NULL,
    nome character varying(20) NOT NULL,
    cognome character varying(20) NOT NULL,
    luogo_nascita character varying(40) NOT NULL,
    data_nascita date NOT NULL,
    nazionalita character varying(25) NOT NULL,
    indirizzo character varying(40) NOT NULL,
    telefono bigint NOT NULL,
    email character varying(40) NOT NULL,
    username character varying(20) NOT NULL,
    password character varying(20) NOT NULL
);



CREATE TABLE public.emergenza (
    id integer NOT NULL,
    nome character varying(20) NOT NULL,
    cognome character varying(20) NOT NULL,
    telefono bigint NOT NULL,
    email character varying(40) NOT NULL
);



ALTER TABLE public.emergenza ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.emergenza_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);



CREATE TABLE public.emergenza_lav (
    id_emergenza integer NOT NULL,
    id_lavoratore integer NOT NULL
);



CREATE TABLE public.esp_lav (
    esperienza character varying(60) NOT NULL,
    id_lavoratore integer NOT NULL
);



CREATE TABLE public.esperienza (
    nome_esperienza character varying(60) NOT NULL
);



CREATE TABLE public.lav_comune (
    comune character varying(60) NOT NULL,
    id_lavoratore integer NOT NULL
);



CREATE TABLE public.lavoratore (
    id integer NOT NULL,
    nome character varying(20) NOT NULL,
    cognome character varying(20) NOT NULL,
    luogo_nascita character varying(40) NOT NULL,
    data_nascita date NOT NULL,
    nazionalita character varying(25) NOT NULL,
    indirizzo character varying(40) NOT NULL,
    telefono bigint,
    email character varying(40) NOT NULL,
    automunito character varying(2) NOT NULL,
    inizio_periodo_disp date NOT NULL,
    fine_periodo_disp date NOT NULL,
    id_dipendente integer NOT NULL
);



CREATE SEQUENCE public.lavoratore_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE public.lavoratore_id_seq OWNED BY public.lavoratore.id;



CREATE TABLE public.lavoro_svolto (
    id integer NOT NULL,
    inizio_periodo date NOT NULL,
    fine_periodo date NOT NULL,
    nome_azienda character varying(40) NOT NULL,
    mansione_svolta character varying(40) NOT NULL,
    luogo_lavoro character varying(40) NOT NULL,
    retribuzione_lorda_giornaliera integer NOT NULL,
    id_lavoratore integer NOT NULL
);



CREATE SEQUENCE public.lavoro_svolto_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE public.lavoro_svolto_id_seq OWNED BY public.lavoro_svolto.id;



CREATE TABLE public.lingua (
    nome_lingua character varying(40) NOT NULL
);



CREATE TABLE public.lingua_lav (
    nome_lingua character varying(40) NOT NULL,
    id_lavoratore integer NOT NULL
);



CREATE TABLE public.pat_lav (
    nome_patente character varying(20) NOT NULL,
    id_lavoratore integer NOT NULL
);



CREATE TABLE public.patente (
    tipo_patente character varying(5) NOT NULL
);



ALTER TABLE ONLY public.lavoratore ALTER COLUMN id SET DEFAULT nextval('public.lavoratore_id_seq'::regclass);



ALTER TABLE ONLY public.lavoro_svolto ALTER COLUMN id SET DEFAULT nextval('public.lavoro_svolto_id_seq'::regclass);



ALTER TABLE ONLY public.comune
    ADD CONSTRAINT comune_pkey PRIMARY KEY (nome_comune);



ALTER TABLE ONLY public.dipendente
    ADD CONSTRAINT dipendente_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.emergenza_lav
    ADD CONSTRAINT emergenza_lav_pkey PRIMARY KEY (id_emergenza, id_lavoratore);



ALTER TABLE ONLY public.emergenza
    ADD CONSTRAINT emergenza_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.esp_lav
    ADD CONSTRAINT esp_lav_pkey PRIMARY KEY (esperienza, id_lavoratore);



ALTER TABLE ONLY public.esperienza
    ADD CONSTRAINT esperienza_pkey PRIMARY KEY (nome_esperienza);



ALTER TABLE ONLY public.lav_comune
    ADD CONSTRAINT lav_comune_pkey PRIMARY KEY (comune, id_lavoratore);



ALTER TABLE ONLY public.lavoratore
    ADD CONSTRAINT lavoratore_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.lavoro_svolto
    ADD CONSTRAINT lavoro_svolto_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.lingua_lav
    ADD CONSTRAINT lingua_lav_pkey PRIMARY KEY (nome_lingua, id_lavoratore);



ALTER TABLE ONLY public.lingua
    ADD CONSTRAINT lingua_pkey PRIMARY KEY (nome_lingua);



ALTER TABLE ONLY public.pat_lav
    ADD CONSTRAINT pat_lav_pkey PRIMARY KEY (nome_patente, id_lavoratore);



ALTER TABLE ONLY public.patente
    ADD CONSTRAINT patente_pkey PRIMARY KEY (tipo_patente);



ALTER TABLE ONLY public.emergenza_lav
    ADD CONSTRAINT emergenza_lav_id_emergenza_fkey FOREIGN KEY (id_emergenza) REFERENCES public.emergenza(id);



ALTER TABLE ONLY public.emergenza_lav
    ADD CONSTRAINT emergenza_lav_id_lavoratore_fkey FOREIGN KEY (id_lavoratore) REFERENCES public.lavoratore(id);



ALTER TABLE ONLY public.esp_lav
    ADD CONSTRAINT esp_lav_esperienza_fkey FOREIGN KEY (esperienza) REFERENCES public.esperienza(nome_esperienza);



ALTER TABLE ONLY public.esp_lav
    ADD CONSTRAINT esp_lav_id_lavoratore_fkey FOREIGN KEY (id_lavoratore) REFERENCES public.lavoratore(id);



ALTER TABLE ONLY public.lav_comune
    ADD CONSTRAINT lav_comune_comune_fkey FOREIGN KEY (comune) REFERENCES public.comune(nome_comune);



ALTER TABLE ONLY public.lav_comune
    ADD CONSTRAINT lav_comune_id_lavoratore_fkey FOREIGN KEY (id_lavoratore) REFERENCES public.lavoratore(id);



ALTER TABLE ONLY public.lavoratore
    ADD CONSTRAINT lavoratore_id_dipendente_fkey FOREIGN KEY (id_dipendente) REFERENCES public.dipendente(id);



ALTER TABLE ONLY public.lavoro_svolto
    ADD CONSTRAINT lavoro_svolto_id_lavoratore_fkey FOREIGN KEY (id_lavoratore) REFERENCES public.lavoratore(id);



ALTER TABLE ONLY public.lingua_lav
    ADD CONSTRAINT lingua_lav_id_lavoratore_fkey FOREIGN KEY (id_lavoratore) REFERENCES public.lavoratore(id);



ALTER TABLE ONLY public.lingua_lav
    ADD CONSTRAINT lingua_lav_nome_lingua_fkey FOREIGN KEY (nome_lingua) REFERENCES public.lingua(nome_lingua);



ALTER TABLE ONLY public.pat_lav
    ADD CONSTRAINT pat_lav_id_lavoratore_fkey FOREIGN KEY (id_lavoratore) REFERENCES public.lavoratore(id);



ALTER TABLE ONLY public.pat_lav
    ADD CONSTRAINT pat_lav_nome_patente_fkey FOREIGN KEY (nome_patente) REFERENCES public.patente(tipo_patente);

--- Dipendente
INSERT INTO public.dipendente VALUES (1,'Tester','Major','FL','1980-01-11','EN','Via Testing',1111111111,'test@test.com','tester01','12345')