CREATE TABLE refresh_tokens (
    id BINARY(16) NOT NULL,
    token VARCHAR(255) NOT NULL,
    user_id BINARY(16) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    revoked_at TIMESTAMP NULL,

    CONSTRAINT pk_refresh_tokens
        PRIMARY KEY (id),

    CONSTRAINT uq_refresh_tokens_token
        UNIQUE (token),

    CONSTRAINT fk_refresh_tokens_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);