-- CREATE ROLE erp_crm LOGIN PASSWORD 'test' NOINHERIT;
-- CREATE ROLE erp     LOGIN PASSWORD 'test' INHERIT;
-- CREATE ROLE crm     LOGIN PASSWORD 'test' INHERIT;

-- Grant erp_crm to erp;
-- Grant erp_crm to crm;

-- CREATE DATABASE io_erp_crm;

DROP SCHEMA erp_crm CASCADE;
DROP SCHEMA erp CASCADE;
DROP SCHEMA crm CASCADE;

CREATE SCHEMA erp_crm AUTHORIZATION schemacrawler;
CREATE SCHEMA erp AUTHORIZATION schemacrawler;
CREATE SCHEMA crm AUTHORIZATION schemacrawler;
-- CREATE SCHEMA erp_crm AUTHORIZATION gros;
-- CREATE SCHEMA erp AUTHORIZATION gros;
-- CREATE SCHEMA crm AUTHORIZATION gros;


/* Common for ERP and CRM */
CREATE TABLE erp_crm.employees (
    id     integer PRIMARY KEY,
    name            varchar(100) NOT NULL,
    telephone       varchar(32),
    mail            varchar(256) NOT NULL UNIQUE,
    password        varchar(256) NOT NULL,
    role            varchar(32)  NOT NULL,
    month_schedule  integer      DEFAULT 0,
    visibility      boolean      DEFAULT 'true'
);

CREATE TABLE erp_crm.clients_types (
    id integer PRIMARY KEY,
    description varchar(256) NOT NULL
);

CREATE TABLE erp_crm.clients (
    id           integer PRIMARY KEY,
    name                varchar(100) NOT NULL,
    mail                varchar(256) NOT NULL,
    street              varchar(100),
    city                varchar(100),
    post_code           varchar(10),
    name_delivery       varchar(100),
    street_delivery     varchar(100),
    city_delivery       varchar(100),
    post_code_delivery  varchar(100),
    nip                 varchar(32),
    telephone           varchar(32),
    client_type_id      integer NOT NULL,
    FOREIGN KEY (client_type_id) REFERENCES erp_crm.clients_types (id)
);


/* ERP part*/
CREATE TABLE erp.units (
    id     integer PRIMARY KEY,
    name        varchar(100) NOT NULL,
    name_short  varchar(32)  NOT NULL
);


CREATE TABLE erp.articles (
    id integer PRIMARY KEY,
    name            varchar(256) NOT NULL,
    availability    integer NOT NULL,
    unit_id         integer NOT NULL,
    unit_price      money   NOT NULL,
    weight          real,
    FOREIGN KEY (unit_id) REFERENCES erp.units (id)
);

CREATE TABLE erp.delivery_costs (
    id integer PRIMARY KEY,
    article_id       integer NOT NULL,
    weight_from      real NOT NULL,
    weight_to        real NOT NULL,
    price            money NOT NULL,
    FOREIGN KEY (article_id) REFERENCES erp.articles (id)
);


CREATE TABLE erp.orders (
    id integer PRIMARY KEY,
    order_number        varchar(32)     NOT NULL,
    order_date          date            NOT NULL,
    realization_date    date,
    realization_deadline varchar(100)   NOT NULL,
    employee_id         integer         NOT NULL,
    client_id           integer         NOT NULL,
    conditions          text            DEFAULT '',
    comments            text            DEFAULT '',
    advance             money           DEFAULT 0,
    vat                 NUMERIC(4, 2)   NOT NULL,
    state               varchar(100)    NOT NULL,
    delivery_cost       money,
    delivery_address    text            NOT NULL,
    signed              boolean         DEFAULT 'false',
    paid                boolean         DEFAULT 'false',
    done                boolean         DEFAULT 'false',
    FOREIGN KEY (employee_id) REFERENCES erp_crm.employees (id),
    FOREIGN KEY (client_id) REFERENCES erp_crm.clients (id)
);


CREATE TABLE erp.ordered_articles_one_type (
    id integer PRIMARY KEY,
    article_id  integer NOT NULL,
    order_id    integer NOT NULL,
    description text    NOT NULL,
    amount      integer NOT NULL,
    unit_price  money,
    net_price   money,
    weight      real,
    FOREIGN KEY (article_id) REFERENCES erp.articles (id),
    FOREIGN KEY (order_id) REFERENCES erp.orders (id)
);

CREATE TABLE erp.proformas (
    id integer PRIMARY KEY,
    proforma_number     varchar(32),
    order_id            integer NOT NULL UNIQUE,
    issue_date          date NOT NULL,
    sale_date           date NOT NULL,
    payment_date        date NOT NULL,
    payment_method      varchar(100) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES erp.orders (id) ON DELETE CASCADE
);




/* CRM part */
CREATE TABLE crm.contacts_groups (
    id integer PRIMARY KEY,
    description varchar(256) NOT NULL
);

CREATE TABLE crm.contacts (
    id integer PRIMARY KEY,
    client_id       integer,
    employee_id     integer NOT NULL,
    contacts_groups_id integer NOT NULL,
    vip             boolean DEFAULT 'false',
    name      varchar(100) NOT NULL,
    street          varchar(100),
    city            varchar(100),
    post_code       varchar(10),
    telephone       varchar(32),
    mail            varchar(256) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES erp_crm.employees (id),
    FOREIGN KEY (client_id) REFERENCES erp_crm.clients (id),
    FOREIGN KEY (contacts_groups_id) REFERENCES crm.contacts_groups (id)
);


CREATE TABLE crm.tasks_statuses (
    id integer PRIMARY KEY,
    description text
);

CREATE TABLE crm.tasks (
    id integer PRIMARY KEY,
    title               varchar(100),
    status_id           integer     NOT NULL,
    task_date           date        NOT NULL,
    employee_id         integer     NOT NULL,
    employee_commissioned_id integer,
    contact_id          integer,
    background_color    varchar(10) NOT NULL,
    description         text,
    is_archived         boolean     DEFAULT 'false',
    end_date            date,
    FOREIGN KEY (status_id) REFERENCES crm.tasks_statuses (id),
    FOREIGN KEY (employee_id) REFERENCES erp_crm.employees (id),
    FOREIGN KEY (employee_commissioned_id) REFERENCES erp_crm.employees (id),
    FOREIGN KEY (contact_id) REFERENCES crm.contacts (id)
);

CREATE TABLE crm.task_comments (
    id integer PRIMARY KEY,
    employee_id  integer NOT NULL,
    content      text NOT NULL,
    comment_time timestamp NOT NULL,
    task_id integer NOT NULL,
    FOREIGN KEY (task_id) REFERENCES crm.tasks (id)
);


CREATE TABLE crm.task_notes (
    id integer PRIMARY KEY,
    background_color varchar(10) NOT NULL,
    content          text DEFAULT '',
    task_id integer,
    FOREIGN KEY (task_id) REFERENCES crm.tasks (id)
);


CREATE TABLE crm.meetings (
    id integer PRIMARY KEY,
    meeting_date        date NOT NULL,
    description         text,
    next_meeting_date   date,
    purpose             varchar(256),
    employee_id         integer NOT NULL,
    telephone_meeting   boolean DEFAULT 'false',
    FOREIGN KEY (employee_id) REFERENCES erp_crm.employees (id)
);

CREATE TABLE crm.meeting_notes (
    id integer PRIMARY KEY,
    background_color varchar(10) NOT NULL,
    content          text DEFAULT '',
    meeting_id integer,
    FOREIGN KEY (meeting_id) REFERENCES crm.meetings (id)
);

CREATE TABLE crm.meeting_contacts (
    id integer PRIMARY KEY,
    meeting_id integer NOT NULL,
    contact_id integer NOT NULL,
    FOREIGN KEY (meeting_id) REFERENCES crm.meetings (id),
    FOREIGN KEY (contact_id) REFERENCES crm.contacts (id)
);

CREATE TABLE crm.meeting_employees (
    id integer PRIMARY KEY,
    meeting_id  integer NOT NULL,
    employee_id integer NOT NULL,
    FOREIGN KEY (meeting_id) REFERENCES crm.meetings (id),
    FOREIGN KEY (employee_id) REFERENCES erp_crm.employees (id)
);

CREATE TABLE crm.mails (
    id integer PRIMARY KEY,
    employee_id integer NOT NULL,
    contact_id  integer NOT NULL,
    title       varchar(256) NOT NULL,
    content     text NOT NULL,
    date        date NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES erp_crm.employees (id),
    FOREIGN KEY (contact_id) REFERENCES crm.contacts (id)
);