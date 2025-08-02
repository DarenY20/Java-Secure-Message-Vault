CREATE DATABASE secure_vault;
USE secure_vault;
CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    encrypted_message TEXT NOT NULL,
    keyword_hash VARCHAR(64) NOT NULL
);
