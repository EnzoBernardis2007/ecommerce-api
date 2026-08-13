CREATE TABLE users (
    id BINARY(16) NOT NULL,

    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,

    username VARCHAR(255) NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    deleted_at TIMESTAMP NULL,

    PRIMARY KEY (id),

    CONSTRAINT chk_users_email_not_empty
        CHECK (CHAR_LENGTH(TRIM(email)) > 0),

    CONSTRAINT chk_users_password_hash_not_empty
        CHECK (CHAR_LENGTH(TRIM(password_hash)) > 0),

    CONSTRAINT chk_users_username_not_empty
        CHECK (CHAR_LENGTH(TRIM(username)) > 0)
);