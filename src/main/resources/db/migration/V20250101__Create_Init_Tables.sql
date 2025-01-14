CREATE TABLE tp_invitation
(
    id          BIGSERIAL PRIMARY KEY, -- 초대장 ID
    title       VARCHAR(255) NOT NULL, -- 초대장 제목
    description TEXT,                  -- 초대장 설명
    start_time  TIMESTAMP    NOT NULL, -- 초대장 시작 시간
    created_at  TIMESTAMP    NOT NULL, -- 초대장 시작 시간
    updated_at  TIMESTAMP    NOT NULL, -- 초대장 시작 시간
    end_time    TIMESTAMP    NOT NULL  -- 초대장 종료 시간
);

CREATE TABLE tp_task
(
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR(255) NOT NULL, -- 할일의 이름
    major_category_id BIGINT,                -- 대분류 ID
    minor_category_id BIGINT,                -- 소분류 ID
    penalty_id        BIGINT                 -- 벌칙 ID
);

CREATE TABLE tp_invitation_task
(
    id                BIGSERIAL PRIMARY KEY,
    invitation_id BIGINT NOT NULL, -- 초대장 ID
    task_id       BIGINT NOT NULL, -- Task ID
    task_order    BIGINT NOT NULL -- 순서
);

CREATE TABLE tp_major_category
(
    id   BIGSERIAL PRIMARY KEY, -- 대분류 ID
    name VARCHAR(255) NOT NULL  -- 대분류 이름
);


CREATE TABLE tp_minor_category
(
    id                BIGSERIAL PRIMARY KEY,
    major_category_id BIGINT       NOT NULL, -- 연결된 대분류 ID
    name              VARCHAR(255) NOT NULL  -- 소분류 이름
);

CREATE TABLE tp_penalty
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,              --벌칙의 이름
    description TEXT,                               -- 벌칙 상세 정보
    is_secret   BOOLEAN      NOT NULL DEFAULT FALSE --개별 사용자가 생성한 벌칙인지
);

CREATE INDEX idx_invitation_id ON tp_invitation_task (invitation_id);
CREATE INDEX idx_task_id ON tp_invitation_task (task_id);