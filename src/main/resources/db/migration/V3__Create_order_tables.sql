--
-- Orders
-- Name: ORDERS; Type: TABLE; Schema: simplysend; Owner: -
--
CREATE TABLE ORDERS (
    ID bigint NOT NULL,
    STATUS character varying(255),
    REASON character varying(255),
    COMMENT character varying(255),
    OWNER_ID bigint NOT NULL
);

CREATE SEQUENCE ORDERS_ID_SEQ
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
;

ALTER TABLE ORDERS ALTER COLUMN id SET DEFAULT nextval('ORDERS_ID_SEQ');

SELECT pg_catalog.setval('ORDERS_ID_SEQ', 1, false);

ALTER TABLE ONLY ORDERS
    ADD CONSTRAINT orders_pkey PRIMARY KEY (ID);

ALTER TABLE ONLY ORDERS
    ADD CONSTRAINT orders_owner_pkft FOREIGN KEY (OWNER_ID) REFERENCES USER_PROFILE(ID);

--
-- Orders Item Join Table
-- Name: ORDERS_ITEM; Type: TABLE; Schema: simplysend; Owner: -
--
CREATE TABLE ORDERS_ITEM (
    ORDER_ID bigint NOT NULL,
    ITEM_ID bigint NOT NULL
);

ALTER TABLE ONLY ORDERS_ITEM
    ADD CONSTRAINT orders_item_pkey PRIMARY KEY (ORDER_ID, ITEM_ID);

ALTER TABLE ONLY ORDERS_ITEM
    ADD CONSTRAINT orders_item_orders_pkft FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID);

ALTER TABLE ONLY ORDERS_ITEM
    ADD CONSTRAINT orders_item_item_pkft FOREIGN KEY (ITEM_ID) REFERENCES ITEM(ID);
