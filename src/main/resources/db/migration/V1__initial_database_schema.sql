CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS accounts(
    id UUID NOT NULL DEFAULT uuid_generate_v1(),
    name varchar(200),
    CONSTRAINT pk_accounts
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users(
    id UUID NOT NULL DEFAULT uuid_generate_v1(),
    account_id UUID NOT NULL,
    first_name varchar(20),
    hid varchar(20) NOT NULL,
    last_name varchar(20),
    CONSTRAINT pk_users
        PRIMARY KEY (id),
    CONSTRAINT fk_users_accounts
        FOREIGN KEY (account_id)
        REFERENCES accounts (id)
);

CREATE TABLE IF NOT EXISTS organizations(
    id UUID NOT NULL DEFAULT uuid_generate_v1(),
    account_id UUID NOT NULL,
    customer_number VARCHAR(40) NOT NULL,
    ein VARCHAR(40) NOT NULL,
    policy_number VARCHAR(40) NOT NULL,
    CONSTRAINT pk_organizations
        PRIMARY KEY (id),
    CONSTRAINT fk_organizations_accounts
        FOREIGN KEY (account_id)
        REFERENCES accounts (id)
);

CREATE TABLE IF NOT EXISTS reports(
    id UUID NOT NULL DEFAULT uuid_generate_v1(),
    organization_id UUID NOT NULL,
    name varchar(100),
    CONSTRAINT pk_reports
        PRIMARY KEY (id),
    CONSTRAINT fk_reports_organizations
        FOREIGN KEY (organization_id)
        REFERENCES organizations (id)
);