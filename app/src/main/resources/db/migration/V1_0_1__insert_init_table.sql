INSERT INTO mkga."user" (username, email, password)
VALUES ('admin', '123456@qq.com', '$2a$10$7zfEdqQYJrBnmDdu7UkgS.zOAsJf4bB1ZYrVhCBAIvIoPbEmeVnVe');

INSERT INTO mkga.role (code, name)
VALUES ('ADMIN', 'ADMIN'),
       ('GENERAL', 'GENERAL');

INSERT INTO mkga.permission (code, name)
VALUES ('READ_POSITION_PERMISSION', 'READ_POSITION_PERMISSION'),
       ('WRITE_POSITION_PERMISSION', 'WRITE_POSITION_PERMISSION'),
       ('READ_DEPARTMENT_PERMISSION', 'READ_DEPARTMENT_PERMISSION'),
       ('WRITE_DEPARTMENT_PERMISSION', 'WRITE_DEPARTMENT_PERMISSION'),
       ('READ_SCHEDULER_PERMISSION', 'READ_SCHEDULER_PERMISSION'),
       ('WRITE_SCHEDULER_PERMISSION', 'WRITE_SCHEDULER_PERMISSION'),
       ('WRITE_USER_ROLE_PERMISSION', 'WRITE_USER_ROLE_PERMISSION'),
       ('READ_USER_ROLE_PERMISSION', 'READ_USER_ROLE_PERMISSION'),
       ('READ_LLM_CONFIG_PERMISSION', 'READ_LLM_CONFIG_PERMISSION'),
       ('WRITE_LLM_CONFIG_PERMISSION', 'WRITE_LLM_CONFIG_PERMISSION');


INSERT INTO mkga.user_role_mapping (user_id, role_id)
VALUES (1, 1);

INSERT INTO mkga.role_permission_mapping (role_id, permission_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10);

INSERT INTO mkga.ai_llm_config (name, code, model_name, api_key, url, enable, priority)
VALUES ('DeepSeek', 'DEEP_SEEK', 'deepseek-chat', 'your_api_key', 'https://api.deepseek.com', false, 0),
       ('智谱清言', 'ZHI_PU', 'glm-4-flash', 'your_api_key', 'https://open.bigmodel.cn/', false, 1);