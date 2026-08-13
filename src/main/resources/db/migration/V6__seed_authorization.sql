-- ============================================================
-- ROLES
-- ============================================================

INSERT INTO roles (
    id,
    name,
    description
)
VALUES
    (
        UUID_TO_BIN(UUID()),
        'ADMIN',
        'Full administrative access'
    ),
    (
        UUID_TO_BIN(UUID()),
        'CUSTOMER',
        'Standard customer access'
    );


-- ============================================================
-- PERMISSIONS
-- ============================================================

INSERT INTO permissions (
    id,
    name,
    description
)
VALUES
    (
        UUID_TO_BIN(UUID()),
        'USER_READ',
        'View users'
    ),
    (
        UUID_TO_BIN(UUID()),
        'USER_CREATE',
        'Create users'
    ),
    (
        UUID_TO_BIN(UUID()),
        'USER_UPDATE',
        'Update users'
    ),
    (
        UUID_TO_BIN(UUID()),
        'USER_DELETE',
        'Delete users'
    );

-- ============================================================
-- ADMIN → ALL USER PERMISSIONS
-- ============================================================

INSERT INTO role_permissions (
    role_id,
    permission_id
)
SELECT
    r.id,
    p.id
FROM roles r
         CROSS JOIN permissions p
WHERE r.name = 'ADMIN';