INSERT INTO account (id, username, password)
VALUES (1, 'admin', '123');

INSERT INTO teacher (id, name, email, teaches_at)
VALUES (1, 'Violeta Sun', 'violetasun@usp.br', 'EACH');

INSERT INTO subject (id, name, abbreviation, code, related_course,
                     teacher_id, created_by_account_id )
VALUES (1, 'Introdução à Administração e Economia para Computação',
        'IAEC', 'ACH2053', 'Sistemas de Informação', 1, 1);