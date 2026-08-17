ALTER TABLE users
DROP CONSTRAINT chk_users_username_not_empty;

ALTER TABLE users
    RENAME COLUMN username TO display_name;

ALTER TABLE users
    ADD CONSTRAINT chk_users_display_name_not_empty
        CHECK (CHAR_LENGTH(TRIM(display_name)) > 0);