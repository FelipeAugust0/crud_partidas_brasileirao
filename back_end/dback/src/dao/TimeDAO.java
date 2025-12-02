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

    // =============================
    // BUSCAR TODOS OS TIMES
    // =============================
    public List<Time> buscarTodos() {
        List<Time> times = new ArrayList<>();

        String sql = "SELECT * FROM time";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Time t = new Time();
                t.setId_time(rs.getLong("id_time"));
                t.setNome(rs.getString("nome"));
                t.setSerie(rs.getString("serie"));
                times.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar times: " + e.getMessage());
        }

        return times;
    }

    // =============================
    // BUSCAR TIMES POR ID
    // =============================
    public Time buscarPorId(Long id) {
        Time t = null;

        String sql = "SELECT * FROM time WHERE id_time = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                t = new Time();
                t.setId_time(rs.getLong("id_time"));
                t.setNome(rs.getString("nome"));
                t.setSerie(rs.getString("serie"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar time por ID: " + e.getMessage());
        }

        return t;
    }

    // =============================
    // BUSCAR TIMES POR SÉRIE
    // =============================
    public List<Time> buscarPorSerie(String serie) {
        List<Time> times = new ArrayList<>();

        String sql = "SELECT id_time, nome, serie FROM time WHERE serie = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, serie);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Time t = new Time(
                        rs.getLong("id_time"),
                        rs.getString("nome"),
                        rs.getString("serie")
                    );
                    times.add(t);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar times por série: " + e.getMessage());
            e.printStackTrace();
        }

        return times;
    }
}

