-- =============================
-- 1) CRIAÇÃO DO BANCO DE DADOS
-- =============================
CREATE DATABASE brasileirao;
USE brasileirao;

-- =============================
-- 2) CRIAÇÃO DAS TABELAS
-- =============================
CREATE TABLE time (
    id_time INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    serie ENUM('A', 'B', 'C') NOT NULL
);

CREATE TABLE partida (
    id_partida INT PRIMARY KEY AUTO_INCREMENT,
    id_time_casa INT NOT NULL,
    id_time_fora INT NOT NULL,
    data_hora_inicio DATETIME NOT NULL,
    duracao_partida DATETIME NOT NULL,
    FOREIGN KEY (id_time_casa) REFERENCES time(id_time),
    FOREIGN KEY (id_time_fora) REFERENCES time(id_time)
);

INSERT INTO time (nome, serie) VALUES
('Atlético-MG', 'A'),
('Bahia', 'A'),
('Botafogo', 'A'),
('Ceará', 'A'),
('Corinthians', 'A'),
('Cruzeiro', 'A'),
('Flamengo', 'A'),
('Fluminense', 'A'),
('Fortaleza', 'A'),
('Juventude', 'A'),
('Grêmio', 'A'),
('Internacional', 'A'),
('Mirassol', 'A'),
('Palmeiras', 'A'),
('RB Bragantino', 'A'),
('Santos', 'A'),
('São Paulo', 'A'),
('Sport', 'A'),
('Vasco', 'A'),
('Vitória', 'A'),
('Amazonas', 'B'),
('América-MG', 'B'),
('Athletic', 'B'),
('Atlético-GO', 'B'),
('Athlético-PR', 'B'),
('Avaí', 'B'),
('Botafogo-SP', 'B'),
('Chapecoense', 'B'),
('CRB', 'B'),
('Criciúma', 'B'),
('Coritiba', 'B'),
('Cuiabá', 'B'),
('Ferroviária', 'B'),
('Goiás', 'B'),
('Novorizontino', 'B'),
('Operário', 'B'),
('Paysandu', 'B'),
('Remo', 'B'),
('Vila Nova', 'B'),
('Volta Redonda', 'B'),
('Náutico', 'C'),
('CSA', 'C'),
('Figueirense', 'C'),
('Tombense', 'C'),
('Confiança', 'C'),
('ABC', 'C'),
('Caxias', 'C'),
('Floresta', 'C'),
('São Bernardo', 'C'),
('Londrina', 'C'),
('Ypiranga', 'C'),
('Botafogo-PB', 'C'),
('Anápolis', 'C'),
('Retrô', 'C'),
('Itabaiana', 'C'),
('Maringá', 'C'),
('Brusque', 'C'),
('Guarani', 'C'),
('Ponte Preta', 'C'),
('Ituano', 'C');
