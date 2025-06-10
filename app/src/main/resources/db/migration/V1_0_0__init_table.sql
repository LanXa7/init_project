CREATE SCHEMA IF NOT EXISTS mkga;

CREATE TABLE mkga."user"
(
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR   NOT NULL,
    email       VARCHAR   NOT NULL,
    password    VARCHAR   NOT NULL,
    avatar      VARCHAR,
    enable      BOOLEAN   NOT NULL DEFAULT TRUE,
    modified_by BIGINT,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_user_unique UNIQUE (username, email)
);

CREATE TABLE mkga.permission
(
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR   NOT NULL UNIQUE,
    name        VARCHAR   NOT NULL UNIQUE,
    created_by  BIGINT,
    modified_by BIGINT,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE mkga.role
(
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR   NOT NULL UNIQUE,
    name        VARCHAR   NOT NULL UNIQUE,
    created_by  BIGINT,
    modified_by BIGINT,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE mkga.role_permission_mapping
(
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES mkga.role (id) ON DELETE RESTRICT,
    FOREIGN KEY (permission_id) REFERENCES mkga.permission (id) ON DELETE RESTRICT
);

CREATE TABLE mkga.user_role_mapping
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES mkga.user (id) ON DELETE RESTRICT,
    FOREIGN KEY (role_id) REFERENCES mkga.role (id) ON DELETE RESTRICT
);

CREATE TABLE mkga.department
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    parent_id   BIGINT,
    created_by  BIGINT,
    modified_by BIGINT,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id)
        REFERENCES mkga.department (id)
        ON DELETE RESTRICT
);

CREATE TABLE mkga.user_department_mapping
(
    user_id       BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, department_id),
    FOREIGN KEY (user_id) REFERENCES mkga.user (id) ON UPDATE NO ACTION ON DELETE RESTRICT,
    FOREIGN KEY (department_id) REFERENCES mkga.department (id) ON UPDATE NO ACTION ON DELETE RESTRICT
);

CREATE TABLE mkga.position
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    created_by  BIGINT,
    modified_by BIGINT,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE mkga.user_position_mapping
(
    user_id     BIGINT NOT NULL,
    position_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, position_id),
    FOREIGN KEY (user_id) REFERENCES mkga.user (id) ON UPDATE NO ACTION ON DELETE RESTRICT,
    FOREIGN KEY (position_id) REFERENCES mkga.position (id) ON UPDATE NO ACTION ON DELETE RESTRICT
);

CREATE TYPE mkga.llm_code_enum AS ENUM (
	'DEEP_SEEK',
	'ZHI_PU'
);


CREATE TABLE mkga.ai_llm_config
(
    id         BIGSERIAL          NOT NULL UNIQUE,
    name       VARCHAR(255)       NOT NULL UNIQUE,
    code       mkga.llm_code_enum NOT NULL UNIQUE,
    model_name VARCHAR(255)       NOT NULL,
    api_key    VARCHAR(255)       NOT NULL,
    url        VARCHAR(255)       NOT NULL,
    enable     BOOLEAN            NOT NULL DEFAULT true,
    priority   SMALLINT           NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);
