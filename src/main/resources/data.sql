-- GENRES
MERGE INTO genres (id, name) VALUES (1, 'Комедия');
MERGE INTO genres (id, name) VALUES (2, 'Драма');
MERGE INTO genres (id, name) VALUES (3, 'Мультфильм');
MERGE INTO genres (id, name) VALUES (4, 'Триллер');
MERGE INTO genres (id, name) VALUES (5, 'Документальный');
MERGE INTO genres (id, name) VALUES (6, 'Боевик');

-- MPA
MERGE INTO mpa (id, name, description) VALUES (1, 'G', 'У фильма нет возрастных ограничений');
MERGE INTO mpa (id, name, description) VALUES (2, 'PG', 'Детям рекомендуется смотреть фильм с родителями');
MERGE INTO mpa (id, name, description) VALUES (3, 'PG-13', 'Детям до 13 лет просмотр не желателен');
MERGE INTO mpa (id, name, description) VALUES (4, 'R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого');
MERGE INTO mpa (id, name, description) VALUES (5, 'NC-17', 'Лицам до 18 лет просмотр запрещён');