package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Partida;
import model.Time;
import util.ConnectionFactory;

public class PartidaDAO {

    // =============================
    // BUSCAR PARTIDAS POR SERIE
    // =============================
    public List<Partida> buscarPorSerie(String serie) {

        List<Partida> lista = new ArrayList<>();

        String sql = "SELECT p.id_partida, p.data_hora_inicio, p.duracao_partida, " +
                "tc.id_time AS id_casa, tc.nome AS nome_casa, tc.serie AS serie_casa, " +
                "tf.id_time AS id_fora, tf.nome AS nome_fora, tf.serie AS serie_fora " +
                "FROM partida p " +
                "INNER JOIN time tc ON p.id_time_casa = tc.id_time " +
                "INNER JOIN time tf ON p.id_time_fora = tf.id_time " +
                "WHERE tc.serie = ? OR tf.serie = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, serie);
            stmt.setString(2, serie);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    Time casa = new Time(
                            rs.getLong("id_casa"),
                            rs.getString("nome_casa"),
                            rs.getString("serie_casa"));

                    Time fora = new Time(
                            rs.getLong("id_fora"),
                            rs.getString("nome_fora"),
                            rs.getString("serie_fora"));

                    lista.add(new Partida(
                            rs.getLong("id_partida"),
                            casa,
                            fora,
                            rs.getTimestamp("data_hora_inicio").toLocalDateTime(),
                            rs.getTimestamp("duracao_partida").toLocalDateTime()
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar partida por série: " + e.getMessage());
        }

        return lista;
    }

    // =============================
    // BUSCAR PARTIDAS POR ID
    // =============================
    public Partida buscarPorId(Long id) {

        String sql = "SELECT p.id_partida, p.data_hora_inicio, p.duracao_partida, " +
                "tc.id_time AS id_casa, tc.nome AS nome_casa, tc.serie AS serie_casa, " +
                "tf.id_time AS id_fora, tf.nome AS nome_fora, tf.serie AS serie_fora " +
                "FROM partida p " +
                "INNER JOIN time tc ON p.id_time_casa = tc.id_time " +
                "INNER JOIN time tf ON p.id_time_fora = tf.id_time " +
                "WHERE p.id_partida = ?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    Time casa = new Time(
                            rs.getLong("id_casa"),
                            rs.getString("nome_casa"),
                            rs.getString("serie_casa"));

                    Time fora = new Time(
                            rs.getLong("id_fora"),
                            rs.getString("nome_fora"),
                            rs.getString("serie_fora"));

                    return new Partida(
                            rs.getLong("id_partida"),
                            casa,
                            fora,
                            rs.getTimestamp("data_hora_inicio").toLocalDateTime(),
                            rs.getTimestamp("duracao_partida").toLocalDateTime());
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar partida por ID: " + e.getMessage());
        }

        return null;
    }

    // =============================
    // CADASTRAR NOVA PARTIDA
    // =============================
    public void inserir(Partida partida) {
        String sql = "INSERT INTO partida (id_time_casa, id_time_fora, data_hora_inicio, duracao_partida) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, partida.getTimeCasa().getId_time());
            stmt.setLong(2, partida.getTimeFora().getId_time());
            stmt.setTimestamp(3, Timestamp.valueOf(partida.getDataHoraInicio()));
            stmt.setTimestamp(4, Timestamp.valueOf(partida.getDuracaoPartida()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    partida.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir partida: " + e.getMessage());
        }
    }

    // =============================
    // EDITAR PARTIDA
    // =============================
    public boolean atualizar(Partida partida) {
        String sql = "UPDATE partida SET id_time_casa=?, id_time_fora=?, data_hora_inicio=?, duracao_partida=? WHERE id_partida=?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, partida.getTimeCasa().getId_time());
            stmt.setLong(2, partida.getTimeFora().getId_time());
            stmt.setTimestamp(3, Timestamp.valueOf(partida.getDataHoraInicio()));
            stmt.setTimestamp(4, Timestamp.valueOf(partida.getDuracaoPartida()));
            stmt.setLong(5, partida.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar partida: " + e.getMessage());
            return false;
        }
    }

    // =============================
    // DELETAR PARTIDA
    // =============================
    public boolean deletar(Long id) {
        String sql = "DELETE FROM partida WHERE id_partida=?";

        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar partida: " + e.getMessage());
            return false;
        }
    }
}
