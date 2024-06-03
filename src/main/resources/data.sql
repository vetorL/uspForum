INSERT INTO account (id, username, password)
VALUES (1, 'admin', '123');

INSERT INTO subject (id, name, abbreviation, code, related_course,
                     teacher_name, created_by_account_id )
VALUES (1, 'Introdução à Administração e Economia para Computação',
        'IAEC', 'ACH2053', 'Sistemas de Informação', 'Violeta Sun', 1);