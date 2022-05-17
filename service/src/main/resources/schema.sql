CREATE TABLE IF NOT EXISTS owners (
    id VARCHAR(100) not null
);

CREATE TABLE IF NOT EXISTS targets (
    id BIGINT not null,
    owner_id VARCHAR(100) not null,
    target VARCHAR(10000) not null
);

CREATE TABLE IF NOT EXISTS commands (
    id BIGINT not null,
    status VARCHAR(100) not null,
);