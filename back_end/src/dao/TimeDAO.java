package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Time;
import util.ConnectionFactory;

public class TimeDAO {

    private Connection conn;

    public TimeDAO() {
        conn = ConnectionFactory.getConnection();
    }

    // =====================================================
    // Retorna todos os times do banco
    // =====================================================
    public List<Time> buscarTodos() {
        List<Time> times = new ArrayList<>();

        String sql = "SELECT id_time, nome, serie, escudo FROM time";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Time t = new Time();
                t.setId_time(rs.getLong("id_time"));
                t.setNome(rs.getString("nome"));
                t.setSerie(rs.getString("serie"));
                t.setEscudo(rs.getString("escudo"));
                times.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar times: " + e.getMessage());
        }

        return times;
    }

    // =====================================================
    // Retorna um time usando o ID
    // =====================================================
    public Time buscarPorId(Long id) {

        Time t = null;

        String sql = "SELECT id_time, nome, serie, escudo FROM time WHERE id_time = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                t = new Time();
                t.setId_time(rs.getLong("id_time"));
                t.setNome(rs.getString("nome"));
                t.setSerie(rs.getString("serie"));
                t.setEscudo(rs.getString("escudo"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar time por ID: " + e.getMessage());
        }

        return t;
    }

    // =====================================================
    // Lista os times filtrando pela série (A, B ou C)
    // =====================================================
    public List<Time> buscarPorSerie(String serie) {
        List<Time> times = new ArrayList<>();

        String sql = "SELECT id_time, nome, serie, escudo FROM time WHERE serie = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, serie);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Time t = new Time(
                    rs.getLong("id_time"),
                    rs.getString("nome"),
                    rs.getString("serie"),
                    rs.getString("escudo")
                );
                times.add(t);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar times por série: " + e.getMessage());
        }

        return times;
    }
}
