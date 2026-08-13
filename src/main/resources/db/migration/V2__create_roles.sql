CREATE TABLE roles (
    id BINARY(16) NOT NULL,

    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    deleted_at TIMESTAMP NULL,

    PRIMARY KEY (id),

    CONSTRAINT uq_roles_name
        UNIQUE (name),

    CONSTRAINT chk_roles_name_not_empty
        CHECK (CHAR_LENGTH(TRIM(name)) > 0)
);