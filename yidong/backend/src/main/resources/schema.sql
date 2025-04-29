-- 创建源数据表
CREATE TABLE IF NOT EXISTS source_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ip VARCHAR(50) NOT NULL,
    business_type VARCHAR(50) NOT NULL,
    network_type VARCHAR(10) CHECK (network_type IN ('4G', '5G')),
    time DATE NOT NULL,
    received_data_count BIGINT,
    received_file_count BIGINT,
    total_file_count BIGINT,
    phone_null_count BIGINT,
    domain_null_count BIGINT,
    dest_ip_null_count BIGINT,
    dest_port_null_count BIGINT,
    source_ip_null_count BIGINT,
    source_port_null_count BIGINT,
    protocol_null_count BIGINT
);

-- 创建计算结果表
CREATE TABLE IF NOT EXISTS calculated_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    business_type VARCHAR(50) NOT NULL,
    network_type VARCHAR(10) CHECK (network_type IN ('4G', '5G')),
    time DATE NOT NULL,
    total_data_count BIGINT,
    received_count BIGINT,
    total_file_count BIGINT,
    received_file_count BIGINT,
    received_rate VARCHAR(50),
    inbound_rate VARCHAR(50),
    phone_null_rate VARCHAR(50),
    domain_null_rate VARCHAR(50),
    dest_ip_null_rate VARCHAR(50),
    dest_port_null_rate VARCHAR(50),
    source_ip_null_rate VARCHAR(50),
    source_port_null_rate VARCHAR(50),
    protocol_null_rate VARCHAR(50)
);
