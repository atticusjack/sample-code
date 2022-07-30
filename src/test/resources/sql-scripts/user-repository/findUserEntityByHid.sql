INSERT INTO accounts (name)
VALUES ('test user repository account');

INSERT INTO users (account_id, first_name, hid, last_name)
VALUES (
    (SELECT id FROM accounts WHERE name = 'test user repository account'),
    'first_name',
    'testHid',
    'last_name');