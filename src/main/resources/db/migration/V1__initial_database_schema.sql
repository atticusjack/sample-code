CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS accounts(
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    name varchar(200),
    CONSTRAINT pk_accounts
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users(
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
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
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    create_timestamp TIMESTAMP NOT NULL DEFAULT current_timestamp,
    customer_number VARCHAR(40) NOT NULL,
    delete_timestamp TIMESTAMP,
    ein VARCHAR(40) NOT NULL,
    policy_number VARCHAR(40) NOT NULL,
    CONSTRAINT pk_organizations
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS accounts_organizations(
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    account_id UUID NOT NULL,
    create_timestamp TIMESTAMP NOT NULL DEFAULT current_timestamp,
    delete_timestamp TIMESTAMP,
    organization_id UUID NOT NULL,
    CONSTRAINT pk_accounts_organizations
        PRIMARY KEY (id),
    CONSTRAINT fk_accounts_organizations__accounts
        FOREIGN KEY (account_id)
        REFERENCES accounts (id),
    CONSTRAINT fk_accounts_organizations__organizations
        FOREIGN KEY (organization_id)
        REFERENCES organizations (id)
);

CREATE TABLE IF NOT EXISTS reports(
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    organization_id UUID NOT NULL,
    name varchar(100),
    CONSTRAINT pk_reports
        PRIMARY KEY (id),
    CONSTRAINT fk_reports_organizations
        FOREIGN KEY (organization_id)
        REFERENCES organizations (id)
);