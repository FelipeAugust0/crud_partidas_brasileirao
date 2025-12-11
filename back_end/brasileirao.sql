-- ==========================================
-- BANCO DE DADOS BRASILEIRÃO (Seu Projeto)
-- ==========================================

DROP DATABASE IF EXISTS brasileirao;
CREATE DATABASE brasileirao;
USE brasileirao;

-- ==========================================
-- TABELA TIME
-- ==========================================

CREATE TABLE time (
    id_time INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    serie CHAR(1) NOT NULL,
    escudo VARCHAR(120) NOT NULL
);

-- ==========================================
-- TABELA PARTIDA
-- ==========================================

CREATE TABLE partida (
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    id_time_casa INT NOT NULL,
    id_time_fora INT NOT NULL,
    data_hora_inicio DATETIME NOT NULL,
    data_hora_fim DATETIME NOT NULL,

    CONSTRAINT fk_time_casa FOREIGN KEY (id_time_casa)
        REFERENCES time(id_time)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_time_fora FOREIGN KEY (id_time_fora)
        REFERENCES time(id_time)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ==========================================
-- INSERÇÃO DOS TIMES DA SÉRIE A
-- ==========================================

INSERT INTO time (nome, serie, escudo) VALUES
('Atletico-MG', 'A', 'atletico-mg.png'),
('Bahia', 'A', 'bahia.png'),
('Botafogo', 'A', 'botafogo.png'),
('Ceara', 'A', 'ceara.png'),
('Corinthians', 'A', 'corinthians.png'),
('Cruzeiro', 'A', 'cruzeiro.png'),
('Flamengo', 'A', 'flamengo.png'),
('Fluminense', 'A', 'fluminense.png'),
('Fortaleza', 'A', 'fortaleza.png'),
('Juventude', 'A', 'juventude.png'),
('Gremio', 'A', 'gremio.png'),
('Internacional', 'A', 'internacional.png'),
('Mirassol', 'A', 'mirassol.png'),
('Palmeiras', 'A', 'palmeiras.png'),
('RB Bragantino', 'A', 'rb-bragantino.png'),
('Santos', 'A', 'santos.png'),
('Sao Paulo', 'A', 'sao-paulo.png'),
('Sport', 'A', 'sport.png'),
('Vasco', 'A', 'vasco.png'),
('Vitoria', 'A', 'vitoria.png');

-- ==========================================
-- INSERÇÃO DOS TIMES DA SÉRIE B
-- ==========================================

INSERT INTO time (nome, serie, escudo) VALUES
('Amazonas', 'B', 'amazonas.png'),
('America-MG', 'B', 'america-mg.png'),
('Athletic', 'B', 'athletic.png'),
('Atletico-GO', 'B', 'atletico-go.png'),
('Athletico-PR', 'B', 'athletico-pr.png'),
('Avai', 'B', 'avai.png'),
('Botafogo-SP', 'B', 'botafogo-sp.png'),
('Chapecoense', 'B', 'chapecoense.png'),
('CRB', 'B', 'crb.png'),
('Criciuma', 'B', 'criciuma.png'),
('Coritiba', 'B', 'coritiba.png'),
('Cuiaba', 'B', 'cuiaba.png'),
('Ferroviaria', 'B', 'ferroviaria.png'),
('Goias', 'B', 'goias.png'),
('Novorizontino', 'B', 'novorizontino.png'),
('Operario', 'B', 'operario.png'),
('Paysandu', 'B', 'paysandu.png'),
('Remo', 'B', 'remo.png'),
('Vila Nova', 'B', 'vila-nova.png'),
('Volta Redonda', 'B', 'volta-redonda.png');

-- ==========================================
-- INSERÇÃO DOS TIMES DA SÉRIE C
-- ==========================================

INSERT INTO time (nome, serie, escudo) VALUES
('Nautico', 'C', 'nautico.png'),
('CSA', 'C', 'csa.png'),
('Figueirense', 'C', 'figueirense.png'),
('Tombense', 'C', 'tombense.png'),
('Confianca', 'C', 'confianca.png'),
('ABC', 'C', 'abc.png'),
('Caxias', 'C', 'caxias.png'),
('Floresta', 'C', 'floresta.png'),
('Sao Bernardo', 'C', 'sao-bernardo.png'),
('Londrina', 'C', 'londrina.png'),
('Ypiranga', 'C', 'ypiranga.png'),
('Botafogo-PB', 'C', 'botafogo-pb.png'),
('Anapolis', 'C', 'anapolis.png'),
('Retro', 'C', 'retro.png'),
('Itabaiana', 'C', 'itabaiana.png'),
('Maringa', 'C', 'maringa.png'),
('Brusque', 'C', 'brusque.png'),
('Guarani', 'C', 'guarani.png'),
('Ponte Preta', 'C', 'ponte-preta.png'),
('Ituano', 'C', 'ituano.png');
