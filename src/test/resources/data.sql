
INSERT INTO users(users_id, name, email, password, enabled, credentials_non_expired, login_count, non_blocked, non_expired, create_at, last_login_at)
VALUES
    (1, '김철수', 'chulsoo.kim@example.com', 'password123', 1, 1, 10, 1, 1, NOW(), NOW()),  -- 김철수는 ADMIN 권한을 기본으로 가짐
    (2, '이영희', 'younghee.lee@example.com', 'password456', 1, 1, 5, 1, 1, NOW(), NOW()),  -- 이영희는 USER 권한을 기본으로 가짐
    (3, '박준영', 'junyoung.park@example.com', 'password789', 1, 1, 2, 1, 1, NOW(), NOW()),  -- 박준영은 MANAGER 권한을 기본으로 가짐
    (4, '최수진', 'sujin.choi@example.com', 'password012', 1, 1, 7, 1, 1, NOW(), NOW()),  -- 최수진은 EDITOR 권한을 기본으로 가짐
    (5, '정미나', 'mina.jung@example.com', 'password345', 1, 1, 3, 1, 1, NOW(), NOW());  -- 정미나는 GUEST 권한을 기본으로 가짐
INSERT INTO authorities(authorities_id, name)
VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER'),
    (3, 'ROLE_MANAGER'),
    (4, 'ROLE_EDITOR'),
    (5, 'ROLE_GUEST');
INSERT INTO authority(authority_id, users_id, authorities_id)
VALUES
    (1, 1, 1),  -- 김철수는 ADMIN 권한을 가짐
    (2, 1, 2),  -- 김철수는 USER 권한도 가짐
    (3, 2, 2),  -- 이영희는 USER 권한을 가짐
    (4, 2, 3),  -- 이영희는 MANAGER 권한도 가짐
    (5, 3, 3),  -- 박준영은 MANAGER 권한을 가짐
    (6, 3, 4),  -- 박준영은 EDITOR 권한도 가짐
    (7, 4, 4),  -- 최수진은 EDITOR 권한을 가짐
    (8, 4, 5),  -- 최수진은 GUEST 권한도 가짐
    (9, 5, 5),  -- 정미나는 GUEST 권한을 가짐
    (10, 5, 1); -- 정미나는 ADMIN 권한도 가짐
