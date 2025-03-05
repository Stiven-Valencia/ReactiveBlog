CREATE TABLE IF NOT EXISTS posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    author VARCHAR(100)
);

INSERT INTO posts (title, content, author) VALUES
('Primer Post', 'Contenido inicial', 'admin'),
('Segundo Post', 'Otro contenido', 'user');