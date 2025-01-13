CREATE TABLE tp_invitation
(
    id          INT PRIMARY KEY,       -- 초대장 ID
    title       VARCHAR(255) NOT NULL, -- 초대장 제목
    description TEXT,                  -- 초대장 설명
    start_time  TIMESTAMP    NOT NULL, -- 초대장 시작 시간
    end_time    TIMESTAMP    NOT NULL  -- 초대장 종료 시간
);

CREATE TABLE tp_task
(
    id                INT PRIMARY KEY,
    name              VARCHAR(255) NOT NULL, -- 할일의 이름
    major_category_id INT          NOT NULL, -- 대분류 ID
    minor_category_id INT          NOT NULL, -- 소분류 ID
    penalty_id        INT          NOT NULL  -- 벌칙 ID
);

CREATE TABLE tp_invitation_task
(
    invitation_id INT NOT NULL, -- 초대장 ID
    task_id       INT NOT NULL, -- Task ID
    task_order    INT NOT NULL, -- 순서
    PRIMARY KEY (invitation_id, task_id)
);

CREATE TABLE tp_major_category
(
    id   INT PRIMARY KEY,      -- 대분류 ID
    name VARCHAR(255) NOT NULL -- 대분류 이름
);


CREATE TABLE tp_minor_category
(
    id                INT PRIMARY KEY,
    major_category_id INT          NOT NULL, -- 연결된 대분류 ID
    name              VARCHAR(255) NOT NULL  -- 소분류 이름
);

CREATE TABLE tp_penalty
(
    id          INT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,              --벌칙의 이름
    description TEXT,                               -- 벌칙 상세 정보
    is_secret   BOOLEAN      NOT NULL DEFAULT FALSE --개별 사용자가 생성한 벌칙인지
);