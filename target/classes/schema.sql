CREATE TABLE IF NOT EXISTS bases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    base_id BIGINT,
    FOREIGN KEY (base_id) REFERENCES bases(id)
);

CREATE TABLE IF NOT EXISTS equipment_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS inventory (
    base_id BIGINT NOT NULL,
    equipment_type_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (base_id, equipment_type_id),
    FOREIGN KEY (base_id) REFERENCES bases(id),
    FOREIGN KEY (equipment_type_id) REFERENCES equipment_types(id)
);

CREATE TABLE IF NOT EXISTS purchases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    base_id BIGINT NOT NULL,
    equipment_type_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    date DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    FOREIGN KEY (base_id) REFERENCES bases(id),
    FOREIGN KEY (equipment_type_id) REFERENCES equipment_types(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS transfers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_base_id BIGINT NOT NULL,
    to_base_id BIGINT NOT NULL,
    equipment_type_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    date DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    FOREIGN KEY (from_base_id) REFERENCES bases(id),
    FOREIGN KEY (to_base_id) REFERENCES bases(id),
    FOREIGN KEY (equipment_type_id) REFERENCES equipment_types(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS assignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    base_id BIGINT NOT NULL,
    equipment_type_id BIGINT NOT NULL,
    personnel_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    date DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    FOREIGN KEY (base_id) REFERENCES bases(id),
    FOREIGN KEY (equipment_type_id) REFERENCES equipment_types(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS expenditures (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    base_id BIGINT NOT NULL,
    equipment_type_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    date DATETIME NOT NULL,
    created_by BIGINT NOT NULL,
    FOREIGN KEY (base_id) REFERENCES bases(id),
    FOREIGN KEY (equipment_type_id) REFERENCES equipment_types(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    action VARCHAR(255) NOT NULL,
    endpoint VARCHAR(255) NOT NULL,
    method VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL,
    timestamp DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS personnel_units (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
