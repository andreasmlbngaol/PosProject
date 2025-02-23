--
-- PostgreSQL database dump
--

-- Dumped from database version 17.3
-- Dumped by pg_dump version 17.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.items (
    code integer NOT NULL,
    name character varying(50) NOT NULL,
    price double precision NOT NULL
);


ALTER TABLE public.items OWNER TO postgres;

--
-- Name: item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.item_id_seq OWNER TO postgres;

--
-- Name: item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.item_id_seq OWNED BY public.items.code;


--
-- Name: items code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items ALTER COLUMN code SET DEFAULT nextval('public.item_id_seq'::regclass);


--
-- Data for Name: items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.items (code, name, price) FROM stdin;
1	Chitato	12000
2	Oreo	9000
3	Taro	8000
4	SilverQueen	25000
5	Indomie	3500
6	Mie Sedaap	3200
7	Pop Mie	7000
8	Le Minerale	4000
9	Aqua	5000
10	Teh Pucuk	6000
11	Sprite	8000
12	Coca Cola	8500
13	Fanta	8200
14	Pulpy Orange	11000
15	Buavita	12000
16	Ultra Milk	7500
17	Frisian Flag	7000
18	Good Day Cappuccino	5000
19	Nescafe	6000
20	Kapal Api	4000
21	ABC Susu	4500
22	Roma Malkist	8500
23	Beng Beng	3000
24	Tic Tac	2500
25	Gery Saluut	7000
26	Gery Chocolatos	5500
27	Choki Choki	4000
28	Pocky	10000
29	KitKat	11000
30	Top Coffee	4500
31	Excelso	20000
32	Luwak White Koffie	6500
33	Nutrisari	2500
34	Milo	9000
35	Energen	3500
36	Susu Beruang	12000
37	Yakult	10000
38	Roti Sari Roti	12000
39	Roti Tawar	14000
40	Bon Cabe	8000
41	Saos ABC	7000
42	Kecap Bango	12000
43	Kecap ABC	11000
44	Sambal Indofood	8500
45	Mayonaise Maestro	14000
46	Chili Sauce Heinz	13500
47	Blue Band	13000
48	Keju Kraft	25000
49	Sosis Kanzler	27000
50	Bakso So Good	29000
\.


--
-- Name: item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.item_id_seq', 50, true);


--
-- Name: items item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.items
    ADD CONSTRAINT item_pkey PRIMARY KEY (code);


--
-- PostgreSQL database dump complete
--

