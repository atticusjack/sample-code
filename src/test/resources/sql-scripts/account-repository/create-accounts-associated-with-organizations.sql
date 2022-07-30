INSERT INTO accounts (name)
VALUES ('account 1'), ('account 2'), ('account 3');

INSERT INTO organizations (account_id, customer_number, ein, policy_number)
VALUES ((SELECT id FROM accounts WHERE name = 'account 1'), 'customer 1', 'ein 1', 'policy 1'),
       ((SELECT id FROM accounts WHERE name = 'account 1'), 'customer 1', 'ein 2', 'policy 1'),
       ((SELECT id FROM accounts WHERE name = 'account 1'), 'customer 2', 'ein 3', 'policy 2'),
       ((SELECT id FROM accounts WHERE name = 'account 2'), 'customer 3', 'ein 4', 'policy 3'),
       ((SELECT id FROM accounts WHERE name = 'account 3'), 'customer 4', 'ein 5', 'policy 4');