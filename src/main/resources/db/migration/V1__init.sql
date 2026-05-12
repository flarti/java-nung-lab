CREATE TABLE users (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       firebase_uid VARCHAR(128) NOT NULL UNIQUE,
       email VARCHAR(255) NOT NULL UNIQUE,
       display_name VARCHAR(255) NULL,
       first_name VARCHAR(100) NULL,
       last_name VARCHAR(100) NULL,
       photo_url VARCHAR(500) NULL,
       provider VARCHAR(50) NULL,
       role VARCHAR(50) NOT NULL DEFAULT 'USER',
       enabled TINYINT(1) NOT NULL DEFAULT 1,
       email_verified TINYINT(1) NOT NULL DEFAULT 0,
       last_login_at TIMESTAMP NULL DEFAULT NULL,
       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

       INDEX idx_users_email (email),
       INDEX idx_users_role (role)
);

-- VDS Platform Tables

CREATE TABLE servers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    owner_id BIGINT NOT NULL,

    name VARCHAR(255) NOT NULL UNIQUE,
    ip_address VARCHAR(45) UNIQUE,

    status VARCHAR(50) NOT NULL DEFAULT 'PROVISIONING',
    os_type VARCHAR(50) NOT NULL,

    cpu_cores INT NOT NULL,
    ram_gb INT NOT NULL,
    storage_gb INT NOT NULL,

    monthly_price DECIMAL(10, 2) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_servers_owner
        FOREIGN KEY (owner_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    INDEX idx_servers_owner (owner_id),
    INDEX idx_servers_status (status),
    INDEX idx_servers_name (name)
);

CREATE TABLE deployments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    server_id BIGINT NOT NULL,

    application_name VARCHAR(255) NOT NULL,
    version VARCHAR(100) NOT NULL,
    port INT NOT NULL,

    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',

    configuration LONGTEXT,

    deployed_at TIMESTAMP NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_deployments_server
        FOREIGN KEY (server_id)
            REFERENCES servers (id)
            ON DELETE CASCADE,

    INDEX idx_deployments_server (server_id),
    INDEX idx_deployments_status (status)
);

CREATE TABLE resource_usage (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    server_id BIGINT NOT NULL,

    cpu_percent DECIMAL(5, 2) NOT NULL,
    ram_percent DECIMAL(5, 2) NOT NULL,
    storage_percent DECIMAL(5, 2) NOT NULL,
    bandwidth_gb DECIMAL(10, 2) NOT NULL,

    timestamp TIMESTAMP NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_resource_usage_server
        FOREIGN KEY (server_id)
            REFERENCES servers (id)
            ON DELETE CASCADE,

    INDEX idx_resource_usage_server (server_id),
    INDEX idx_resource_usage_timestamp (server_id, timestamp)
);
