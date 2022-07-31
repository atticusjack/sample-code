INSERT INTO accounts (name)
VALUES ('account 1'), ('account 2');

INSERT INTO users (account_id, first_name, hid, last_name)
VALUES ((SELECT id FROM accounts WHERE name = 'account 1'), 'correct', 'testHid', 'user'),
       ((SELECT id FROM accounts WHERE name = 'account 2'), 'wrong', '654321', 'user');

INSERT INTO organizations (account_id, customer_number, ein, policy_number)
VALUES ((SELECT id FROM accounts WHERE name = 'account 1'), 'customer 1', 'ein 1', 'policy 1'),
       ((SELECT id FROM accounts WHERE name = 'account 1'), 'customer 2', 'ein 2', 'policy 2'),
       ((SELECT id FROM accounts WHERE name = 'account 2'), 'customer 3', 'ein 3', 'policy 3');
